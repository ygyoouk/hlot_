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

      <template v-slot:item.actions="{ item }">
        <v-btn
          v-if="item.contrYn === 'Y' && item.confirmYn === 'Y'"
          color="primary"
          @click="openContr(item)"
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

const headers = [
  {title: '원계약 명', key: 'topContrNm'},
  {title: '업체 명', key: 'compNm'},
  {title: '견적 구분', key: 'estimateDiv'},
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
    /* 상세화면 */
    openDetail: (item, row) => {
      if(!validUtil.isNull(item.target.cellIndex)){
        store.commit("toggleModal", {key: row.item.estimateId , mode:MODAL_MODE.DETAIL});
      }
    },
    /* 계약등록화면 */
    openContr(item) {
      console.log(item);
    },

  }
};
</script>
