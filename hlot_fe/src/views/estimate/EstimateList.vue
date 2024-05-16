<template>
  <EstimateModal
    v-if="store.getters.isOpenModal"
    @update="getEstimates"
  />

  <v-card class="table-container_mt">
    <div class="table-title_mt">
      견적서 관리
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
      :items="estimates"
      :search="search"
      :items-per-page-options="ITEMS_PER_PAGE_OPTIONS"
      class="elevation-1 table-list_mt"
    >


    </v-data-table>
  </v-card>

  <popUp v-if="store.getters.isOpenModal" @click="close()"></popUp>
</template>

<script setup>
import {ITEMS_PER_PAGE_OPTIONS, MODAL_MODE} from "@/util/config";
import EstimateModal from "@/components/modal/EstimateModal.vue";

const headers = [
  {title: '원계약 명', key: 'topContrNm'},
  {title: '업체 명', key: 'compNm'},
  {title: '견적 구분', key: 'estimateDiv'},
  {title: '견적 순번', key: 'orderNo'},
  {title: '확정 여부', key: 'confirmYn'},
  {title: '계약 여부', key: 'contrYn'},
];
</script>

<script>
import store from "@/store/store";
import estimateApi from '@/api/estimate.js'
import {MODAL_MODE} from "@/util/config";

export default {
  beforeMount() {
    this.getEstimates();
  },
  data() {
    return {
      search: '',
      estimates: [],
    };
  },
  methods: {
    /* ESTIMATE 목록 조회 */
    async getEstimates() {
      this.estimates = await estimateApi.estimates();
    },

    /* 등록화면 */
    openReg() {
      store.commit('toggleModal', {key: '', mode: MODAL_MODE.REG});
    },

    openDetail: (item, row) => {
      store.commit("toggleModal");
    },

    close() {
      this.popUpValue = false;
    }
  }
};
</script>
