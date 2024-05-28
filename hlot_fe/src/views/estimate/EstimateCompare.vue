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
        label="견적구분"
        :items="estimateDivs"
        item-title="codeNm"
        item-value="code"
        v-model="searchCondition.estimateDiv"
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
    <div class="pdf-elem" v-for="p in pdfs">
      <p style="text-align: right; background-color: skyblue">
        <span>{{ p.topContrNm }}</span>
        <span>{{ p.compNm }}</span>
        <span>{{ p.estimateDivNm }}</span>
        <span>{{ p.orderNo }}</span>
      </p>
      <VuePDF :pdf="p.pdfObj.pdf" scale="1.1"></VuePDF>
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
  {title: '견적 구분', key: 'estimateDivNm'},
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
    await this.getEstimates();
  },
  data() {
    return {
      compDiv: '',
      estimateDivs: [],
      searchCondition: {
        topContrId: '',
        topContrNm: '',
        compId: '',
        compNm: '',
        estimateDiv: '',
      },

      selected: [],
      estimates: [],
      pdfs: [],
      tempLen: 0,

      bTopContrSearch: false,
      bCompanySearch: false,
      bEstimateModal: false,
      bContrModal: false,
    };
  },
  watch: {
    selected: function(selected) {
      const BASE_URL = import.meta.env.VITE_BASE_URL;
      const BE_MANAGEMENT_PORT = import.meta.env.VITE_BE_MANAGEMENT_PORT;
      const REQUEST_URL = `${BASE_URL}:${BE_MANAGEMENT_PORT}`;

      this.pdfs = this.selected.map(v => {
        console.log(v);
        return {
          topContrNm: v.topContrNm,
          compNm: v.compNm,
          estimateDivNm: v.estimateDivNm,
          orderNo: v.orderNo,
          pdfObj: usePDF(`${REQUEST_URL}/common/download/${v.estimateFileId}`)
        }
      });
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
  }
}
</script>

<style scoped>
  .pdf-container {
    display: flex;
    overflow-x: scroll;
  }

</style>

<style>
.pdf-elem canvas {
  width: 654px !important;
  height: 925px !important;
  border: 1px solid black;
}
.pdf-elem span {
  border: 1px solid black;
  padding: 15px;

}
</style>
