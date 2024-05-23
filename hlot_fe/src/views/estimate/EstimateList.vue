<template>
  <EstimateModal
    v-if="bEstimateModal"
    @update="getEstimates"
    @close="bEstimateModal = false"
  />

  <ContrModal
    v-if="bContrModal"
    @update="getEstimates"
    @close="bContrModal = false"
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
      견적서 등록
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


    <div class="table-btn-list">
      <v-btn color="#5865f2" @click="openReg">등록</v-btn>
    </div>

    <v-data-table
      @click:row="openDetail"
      :headers="headers"
      :items="estimates"
      :items-per-page-options="ITEMS_PER_PAGE_OPTIONS"
      class="elevation-1 table-list_mt"
    >

      <template v-slot:item.actions="{ item }">
        <v-btn
          v-if="item.contrYn === 'N' && item.confirmYn === 'Y'"
          color="primary"
          @click="openContr(item)"
          rounded="xl"
        >
          계약 등록
        </v-btn>
      </template>


    </v-data-table>
  </v-card>

</template>

<script setup>
import {ITEMS_PER_PAGE_OPTIONS, MODAL_MODE} from "@/util/config";
import EstimateModal from "@/components/modal/EstimateModal.vue";
import ContrModal from "@/components/modal/ContrtModal.vue";
import TopContrSearch from "@/components/modal/search/TopContrSearch.vue";
import CompanySearch from "@/components/modal/search/CompanySearch.vue";

const headers = [
  {title: '원계약 명', key: 'topContrNm'},
  {title: '업체 명', key: 'compNm'},
  {title: '견적 구분', key: 'estimateDivNm'},
  {title: '견적 순번', key: 'orderNo'},
  {title: '확정 여부', key: 'confirmYn'},
  {title: '계약 여부', key: 'contrYn'},
  {title: '', key: 'actions'},
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
      estimates: [],

      bTopContrSearch: false,
      bCompanySearch: false,
      bEstimateModal: false,
      bContrModal: false,
    };
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

    /* 등록화면 */
    openReg() {
      store.commit('setModalParams', {key: '', mode: MODAL_MODE.REG});
      this.bEstimateModal = !this.bEstimateModal;
    },
    /* 상세화면 */
    openDetail(item, row) {
      if(!validUtil.isNull(item.target.cellIndex)){
        store.commit("setModalParams", {key: row.item.estimateId , mode:MODAL_MODE.DETAIL});
        this.bEstimateModal = !this.bEstimateModal;
      }
    },
    /* 계약등록화면 */
    openContr(item) {
      const params = {
        estimateId : item.estimateId,
        compId: item.compId,
        topContrId: item.topContrId,

        mode: MODAL_MODE.REG
      };
      store.commit("setModalParams", params);
      this.bContrModal = !this.bContrModal;
    },

  }
};
</script>
