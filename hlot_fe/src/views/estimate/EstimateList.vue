<template>
  <EstimateModal v-if="store.getters.isOpenModal" />

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
      <v-btn color="#5865f2" @click="pushRegPop">등록</v-btn>
    </div>

    <v-data-table
      :headers="headers"
      :items="estimates"
      item-value="estimateNumber"
      v-model="selected"
      :search="search"
      select-strategy="page"
      :items-per-page-options="itemsPerPageOptions"
      class="elevation-1 table-list_mt"
      show-select
      @click:row="popUpOpen"
    >


    </v-data-table>
  </v-card>

  <popUp v-if="store.getters.isOpenModal" @click="close()"></popUp>
</template>

<script setup>
  const itemsPerPageOptions = [
    {value: 10, title: '10'},
    {value: 25, title: '25'},
    {value: 50, title: '50'},
    {value: 100, title: '100'},
  ];

  const headers = [
    { title: '견적 번호', key:'estimateNumber' },
    { title: '고객명', key:'customerName'},
    { title: '생성 날짜', key:'dateCreated'},
    { title: '총액',  key:'totalAmount'},
  ];
</script>

<script>

import EstimateModal from "@/components/modal/EstimateModal.vue";
import store from "@/store/store";

export default {
  mounted() {
  },
  computed: {
      store() {
        return store
      }
    },
  data() {
    return {
      search: '',
      estimates: [
        // 샘플 데이터
        { estimateNumber: '001', customerName: '홍길동', dateCreated: '2024-04-01', totalAmount: '₩1,000,000' },
        { estimateNumber: '002', customerName: '김철수', dateCreated: '2024-04-02', totalAmount: '₩1,500,000' },
      ],
      selected : [],
      popUpValue : false,
    };
  },
  methods: {
    saveItem() {
      // 등록 로직
    },
    popUpOpen(event,{item}){
      this.$store.commit("toggleModal");
      
    },

    pushRegPop: () => {
      store.commit("toggleModal");
    },
    
    close(){
      this.popUpValue = false;
    }
  }
};
</script>
