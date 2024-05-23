<template>
  <CompanyModal
    v-if="bCompanyModal"
    @close="bCompanyModal = !bCompanyModal"
    @update="getCompanys"
  />

  <v-card class="table-container_mt">
    <div class="table-title_mt">
      업체 관리
    </div>

    <div class="d-flex flex-wrap ga-3 mb-0">
      <v-select
        label="업체구분"
        density="compact"
        :items="compDivs"
        item-title="codeNm"
        item-value="code"
        v-model="searchCondition.compDiv"
      />

      <v-text-field
        label="업체명"
        density="compact"
        v-model="searchCondition.compNm"
      />

      <v-text-field
        label="사업자 등록번호"
        density="compact"
        v-model="searchCondition.compBussRegnum"
      />
      <v-btn
        color="green"
        @click="getCompanys"
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
      :items="companys"
      :items-per-page-options="ITEMS_PER_PAGE_OPTIONS"
      class="elevation-1 table-list_mt"
    >

    </v-data-table>
  </v-card>

</template>

<script setup>
import CompanyModal from "@/components/modal/CompanyModal.vue";
import {ITEMS_PER_PAGE_OPTIONS, MODAL_MODE} from "@/util/config";

const headers = [
  { title: '업체 구분', key:'compDivNm'},
  { title: '업체 명', key:'compNm' },
  { title: '사업자등록번호', key:'compBussRegnum' },
  { title: '업체 대표', key:'compCeoNm'},
  { title: '업체 전화번호', key:'compTel'},
  { title: '업체 주소',  key:'compAddr'},
  { title: '등록자',  key:'registUserName'},
  { title: '등록일자',  key:'registDate'}
];
</script>

<script>
import companyApi from '@/api/company.js'
import store from "@/store/store";
import {MODAL_MODE} from "@/util/config";
import commonApi from "@/api/common.js";

export default {
  async beforeMount() {

    await this.getCompanys();

    /* 업체구분 공통코드 조회 */
    this.compDivs = await commonApi.cmmCodeComp("COMP");
  },
  data() {
    return {
      searchCondition: {
        compDiv: '',
        compNm: '',
        compBussRegnum: '',
      },
      compDivs: [],

      companys: [],   // 업체 Array
      bCompanyModal: false,
    };
  },
  methods: {
    /* company 목록 조회 */
    async getCompanys(){
      this.companys = await companyApi.companys(this.searchCondition);
    },

    /* 등록화면 */
    openReg(){
      store.commit('setModalParams', {key: '', mode: MODAL_MODE.REG});
      this.bCompanyModal = !this.bCompanyModal;
    },

    /* 상세조회화면 */
    openDetail(item, row) {
      store.commit('setModalParams', {key: row.item.compId, mode: MODAL_MODE.DETAIL});
      this.bCompanyModal = !this.bCompanyModal;
    }
  }
};
</script>
