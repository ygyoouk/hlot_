<template>
  <CompanyModal
    v-if="store.getters.isOpenModal"
    @update="getCompanys"
  />

  <v-card class="table-container_mt">
    <div class="table-title_mt">
      업체 관리
    </div>

    <v-card-title>
      <v-text-field
        v-model="search"
        prepend-inner-icon="mdi-magnify"
        label="검색"
      ></v-text-field>
    </v-card-title>

    <div class="table-btn-list">
      <v-btn color="#5865f2" @click="openReg">등록</v-btn>
    </div>

    <v-data-table
      @click:row="openDetail"
      :headers="headers"
      :items="companys"
      :search="search"
      :items-per-page-options="ITEMS_PER_PAGE_OPTIONS"
      class="elevation-1 table-list_mt"
    >

    </v-data-table>
  </v-card>

</template>

<script setup>
import CompanyModal from "@/components/modal/CompanyModal.vue";
import store from "@/store/store";
import {ITEMS_PER_PAGE_OPTIONS, MODAL_MODE} from "@/util/config";

const headers = [
  { title: '업체 명', key:'compName' },
  { title: '업체 대표', key:'compCeoNm'},
  { title: '업체 전화번호', key:'compTel'},
  { title: '업체 주소',  key:'compAddr'}
];
</script>

<script>
import companyApi from '@/api/company.js'
import store from "@/store/store";
import {MODAL_MODE} from "@/util/config";
export default {
  beforeMount() {
    this.getCompanys();
  },
  data() {
    return {
      search: '',     // 검색텍스트
      companys: [],   // 업체 Array
    };
  },
  methods: {
    /* company 목록 조회 */
    async getCompanys(){
      this.companys = await companyApi.companys();
    },

    /* 등록화면 */
    openReg(){
      store.commit('toggleModal', {key: '', mode: MODAL_MODE.REG});
    },

    /* 상세조회화면 */
    openDetail(item, row) {
      store.commit('toggleModal', {key: row.item.compId, mode: MODAL_MODE.DETAIL});
    }
  }
};
</script>
