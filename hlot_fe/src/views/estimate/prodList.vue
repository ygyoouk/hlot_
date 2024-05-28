<template>


  <v-card class="table-container_mt">
    <div class="table-title_mt">
      품목 조회
    </div>

    <div class="d-flex flex-wrap ga-3 mb-0">
      <v-text-field
        label="품명"
        v-model="searchCondition.prodNm"
        density="compact"
      ></v-text-field>

      <v-text-field
        label="세부품명"
        v-model="searchCondition.detailProdNm"
        density="compact"
      ></v-text-field>

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
      @click:row="openDetail"
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

const headers = [
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
        prodNm: '',
        detailProdNm: '',
      },
      prods: [],
      id : '',
    };
  },
  methods: {
   
    /* ESTIMATE 목록 조회 */
    async getProds() {
      this.prods = await prodApi.prods(this.id, this.searchCondition);
    },

  }
};
</script>
