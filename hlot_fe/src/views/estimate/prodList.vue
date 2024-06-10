<template>
  <EstimateModal
    v-if="bEstimateModal"
    @update="getEstimates"
    @close="bEstimateModal = false"
  />

  <v-card class="table-container_mt">
    <div class="table-title_mt">
      품목 조회
    </div>

    <div class="d-flex flex-wrap ga-3 mb-0">
      <v-row>
        <v-col cols="3">
          <v-text-field
            label="품명"
            v-model="searchCondition.prodNm"
            @keyup="enterKey()"
            density="compact"
          ></v-text-field>
        </v-col>

        <v-col cols="3">
          <v-text-field
            label="업체명"
            v-model="searchCondition.compNm"
            @keyup="enterKey()"
            density="compact"
          ></v-text-field>
        </v-col>
      </v-row>

      <v-btn
        color="green"
        @click="getProds"
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
      @click:row="openEstimate"
      :headers="headers"
      :items="prods"
      :items-per-page-options="ITEMS_PER_PAGE_OPTIONS"
      class="elevation-1 table-list_mt"
    >
    </v-data-table>
  </v-card>

</template>

<script setup>
import {ITEMS_PER_PAGE_OPTIONS, MODAL_MODE} from "@/util/config";
import EstimateModal from "@/components/modal/EstimateModal.vue";

const headers = [
  {title: '업체명', key: 'compNm'},
  {title: '품명', key: 'prodNm'},
  {title: '세부품명', key: 'detailProdNm'},
  {title: '소비자단가', key: 'clientUnitPrice'},
  {title: '공급단가', key: 'provProdPrice'},
  {title: '할인율', key: 'dcPer'},
  {title: '수량', key: 'quantity'},
];
</script>

<script>
import store from "@/store/store";
import prodApi from '@/api/prod.js'
import {MODAL_MODE} from "@/util/config";
import validUtil from "@/util/validUtil";
import commonApi from "@/api/common";

export default {

  data() {
    return {
      searchCondition: {
        prodNm : '',
        compNm : '',
      },

      bEstimateModal: false,

      prods: [],
      id : '',
    };
  },
  methods: {

    /* ESTIMATE 목록 조회 */
    async getProds() {
      this.prods = await prodApi.prods(this.id, this.searchCondition);

      if(this.prods.length <= 0){
        alert("조회된 내용이 없습니다.");
      }
    },

    enterKey(){
      if(window.event.keyCode == 13){

        this.getProds();
      }
    },

    /*견적상세로 이동*/
    openEstimate(event,{item}){
      console.log(item.estimateId);

      const estimateId = item.estimateId;

      store.commit("setModalParams", {key: estimateId , mode:MODAL_MODE.DETAIL});
      this.bEstimateModal = !this.bEstimateModal;

    }

  }
};
</script>
