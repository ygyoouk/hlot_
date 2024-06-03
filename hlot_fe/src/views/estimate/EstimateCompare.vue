<template>
  <EstimateModal
    v-if="bEstimateModal"
    @update="getEstimates"
    @close="bEstimateModal = false"
  />

  <TopContrSearch
    v-if="bTopContrSearch"
    @close="bTopContrSearch = !bTopContrSearch"
    @select="selectTopContr"
  />

  <CompanySearch
    v-if="bCompanySearch"
    @close="bCompanySearch = !bCompanySearch"
    @select="selectCompany"
    :compDiv="compDiv"
  />

  <div>
    <v-btn
      color="red"
      style="position: fixed; bottom: 5%; z-index: 1; right: 9%;"
      @click="moveScreen('up')"
    >
      ▲
    </v-btn>
    <v-btn
      color="red"
      style="position: fixed; bottom: 5%; z-index: 1; right: 5%;"
      @click="moveScreen('down')"
    >
      ▼
    </v-btn>
  </div>
  <v-card class="table-container_mt">

    <div class="table-title_mt">
      견적서 비교
    </div>
    <div class="d-flex flex-wrap ga-3 mb-0">
      <v-text-field
        label="원계약 명"
        readonly="readonly"
        append-inner-icon="mdi-magnify"
        @click:append-inner="openTopContrSearch"
        v-model="searchCondition.topContrNm"
        density="compact"
      ></v-text-field>

      <v-text-field
        label="업체"
        readonly="readonly"
        append-inner-icon="mdi-magnify"
        @click:append-inner="openCompanySeach('COMP01')"
        v-model="searchCondition.compNm"
        density="compact"
      ></v-text-field>

      <v-select
        label="견적구분(대)"
        :items="estimateDivs"
        item-title="codeNm"
        item-value="code"
        v-model="searchCondition.estimateDiv"
        density="compact"
        @update:modelValue="filterDiv"
      ></v-select>

      <v-select
        label="견적구분(소)"
        :items="fEstimateLowDivs"
        item-title="codeNm"
        item-value="code"
        v-model="searchCondition.estimateLowDiv"
        density="compact"
      ></v-select>
      <v-btn
        color="green"
        @click="getEstimates"
      >
        조회
      </v-btn>
      <v-btn
        color="green"
        @click="searchCondition = {}"
      >
        초기화
      </v-btn>
    </div>

    <v-data-table
      v-model="selected"
      @click:row="openDetail"
      :headers="headers"
      :items="estimates"
      item-value="estimateFileId"
      :items-per-page-options="ITEMS_PER_PAGE_OPTIONS"
      class="elevation-1 table-list_mt"
      return-object
      show-select
    ></v-data-table>
  </v-card>

  <hr>
  <div class="pdf-container">
    <div class="pdf-elem" v-for="p in selectedPdfs">

      <p class="title" >
        <span>
          {{ p.topContrNm }} - {{ p.compNm }}<br>
          ( {{ p.estimateDivNm }} / {{ p.estimateLowDivNm }} ) - {{ p.orderNo }}
        </span>
      </p>
      <div class="pdf-box">
        <div class="btn-group">
          <span v-if="p.pdfObj.pages > 0" @click="zoom(p.tempKey, 0)">-</span>
          <span v-if="p.pdfObj.pages > 0" @click="zoom(p.tempKey, 1)">+</span>
          <span @click="deleteEstimate(p.tempKey)" style="background-color: red;">x</span>
        </div>
        <VuePDF :id="p.tempKey" v-if="p.pdfObj.pages > 0" :pdf="p.pdfObj.pdf" scale="1.1"></VuePDF>
        <div v-else style=" font-size: 21px;">
          PDF 파일이 존재하지 않습니다.
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import {ITEMS_PER_PAGE_OPTIONS, MODAL_MODE} from "@/util/config";
import EstimateModal from "@/components/modal/EstimateModal.vue";
import TopContrSearch from "@/components/modal/search/TopContrSearch.vue";
import CompanySearch from "@/components/modal/search/CompanySearch.vue";
import {VuePDF} from "@tato30/vue-pdf";

const headers = [
  {title: '원계약 명', key: 'topContrNm'},
  {title: '업체 명', key: 'compNm'},
  {title: '견적 구분(대)', key: 'estimateDivNm'},
  {title: '견적 구분(소)', key: 'estimateLowDivNm'},

  {title: '견적 순번', key: 'orderNo'},
  {title: '계약 여부', key: 'contrYn'},
  {title: '등록자', key: 'registUserName'},
  {title: '등록일자', key: 'registDate'},

];
</script>

<script>
import store from "@/store/store";
import estimateApi from '@/api/estimate.js'
import {MODAL_MODE} from "@/util/config";
import validUtil from "@/util/validUtil";
import commonApi from "@/api/common";
import { VuePDF, usePDF } from '@tato30/vue-pdf'

export default {
  async beforeMount() {
    this.estimateDivs = await commonApi.cmmCodeComp('ESTD');
    this.estimateLowDivs = await commonApi.cmmCodeComp('ESTDL');
    // await this.getEstimates();
  },
  mounted() {
    this.addEvent();
  },
  data() {
    return {
      compDiv: '',
      estimateDivs: [],
      estimateLowDivs: [],
      fEstimateLowDivs: [],

      searchCondition: {
        topContrId: '',
        topContrNm: '',
        compId: '',
        compNm: '',
        estimateDiv: '',
        estimateLowDiv: '',
      },

      selected: [],
      estimates: [],
      pdfs: [],
      tempLen: 0,

      bTopContrSearch: false,
      bCompanySearch: false,
      bEstimateModal: false,
      bContrModal: false,

      BASE_URL : import.meta.env.VITE_BASE_URL,
      BE_MANAGEMENT_PORT : import.meta.env.VITE_BE_MANAGEMENT_PORT,
    };
  },
  computed: {
    selectedPdfs() {
      const REQUEST_URL = `${this.BASE_URL}:${this.BE_MANAGEMENT_PORT}`;

      this.selected.forEach(v => {
        if(validUtil.isNull(v.tempKey)){
          v.tempKey = Math.random().toString(36);
          v.pdfObj  = usePDF(`${REQUEST_URL}/common/download/${v.estimateFileId}`);
        }
      });

      return this.selected;
    },
  },
  methods: {
    /* 원계약명 조회 */
    openTopContrSearch() {
      this.bTopContrSearch = !this.bTopContrSearch;
    },
    selectTopContr(obj) {
      this.searchCondition.topContrId = obj.id;
      this.searchCondition.topContrNm = obj.nm;
    },
    /* 업체 조회 */
    openCompanySeach(compDiv) {
      this.compDiv = compDiv;
      this.bCompanySearch = !this.bCompanySearch;
    },
    selectCompany(obj) {
      this.searchCondition.compNm = obj.nm;
      this.searchCondition.compId = obj.id;
    },

    /* ESTIMATE 목록 조회 */
    async getEstimates() {
      this.estimates = await estimateApi.estimates(this.searchCondition);
    },

    /* 상세화면 */
    openDetail(item, row) {
      if(!validUtil.isNull(item.target.cellIndex))
        store.commit("setModalParams", {key: row.item.estimateId , mode:MODAL_MODE.DETAIL});
        this.bEstimateModal = !this.bEstimateModal;
    },

    /* 견적하위구분 필터링 */
    filterDiv() {
      this.searchCondition.estimateLowDiv = '';
      this.fEstimateLowDivs = this.estimateLowDivs.filter(e => {
        return e.upperCode === this.searchCondition.estimateDiv;
      });

    },

    /* 견적서 제외 */
    deleteEstimate(key) {
      this.selected = this.selected.filter(v => v.tempKey !== key);
    },











    /* UI 관련 이벤트 - 줌*/
    zoom(key, type) {
      let n = validUtil.isNull(document.getElementById(key).style.zoom) ? 1 :
        parseFloat(document.getElementById(key).style.zoom);
      if(type === 0) {
        n -= 0.1;
      } else {
        n += 0.1;
      }
      document.getElementById(key).style.zoom = n;
    },

    /* UI 관련 이벤트 - 위아래*/
    moveScreen(opt) {
      if(opt === 'up') {
        window.scrollBy(0, -1000);
      } else {
        window.scrollBy(0, 1000);
      }

    },

    /* UI 관련 이벤트 - 드래그*/
    addEvent() {
      const slider = document.querySelector('.pdf-container');
      let isDown = false;
      let startX;
      let scrollLeft;

      slider.addEventListener('mousedown', e => {
        isDown = true;
        slider.classList.add('active');
        startX = e.pageX - slider.offsetLeft;
        scrollLeft = slider.scrollLeft;
      });

      slider.addEventListener('mouseleave', () => {
        isDown = false;
        slider.classList.remove('active');
      });

      slider.addEventListener('mouseup', () => {
        isDown = false;
        slider.classList.remove('active');
      });

      slider.addEventListener('mousemove', e => {
        if (!isDown) return;
        e.preventDefault();
        const x = e.pageX - slider.offsetLeft;
        const walk = x - startX;
        slider.scrollLeft = scrollLeft - walk;
      });
    }
  }
}



</script>

<style scoped>
  .pdf-container {
    display: flex;
    overflow-x: auto;
  }

  .pdf-elem {
    position: relative;
  }

  .pdf-elem .pdf-box {
    width: 654px !important;
    height: 779px !important;
    overflow: auto;
    position: relative;
    border: 1px solid black;
  }

  .pdf-elem canvas {
    border: 1px solid black;
  }

  .pdf-elem .title {
    width: 654px;
    border: 1px solid black;
    text-align: center;
    background-color: black;
    color: white;
    overflow: hidden;
  }

  .pdf-elem .btn-group {
    position: absolute;
    top: 30px;
    right: 25px;
    z-index: 1;
  }

  .pdf-elem .btn-group span {
    background-color: #4CAF50;
    color: white;
    padding: 1px 5px;
    margin: 2px;
    border-radius: 10px;
    font-size: 17px;
    cursor: pointer;
  }

</style>
