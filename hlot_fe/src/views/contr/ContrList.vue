<template>
  <ContrModal v-if="store.getters.isOpenModal"/>

  <v-card class="table-container_mt">
    <div class="table-title_mt">
      계약 관리
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

       <!-- <v-btn
          color="red"
          @click="deleteProject"
        >삭제</v-btn> -->
    </div>

    <v-data-table
      :headers="headers"
      :items="contrs"
      item-value="contrId"
      v-model="selected"
      :search="search"
      select-strategy="page"
      :items-per-page-options="ITEMS_PER_PAGE_OPTIONS"
      class="elevation-1 table-list_mt"
      show-select
      @click:row="popUpOpen"
      @emitSelectProjectList="selectProjectList"
    >
    </v-data-table>
    
  </v-card>


</template>

<script setup>
  
  import ContrModal from "@/components/modal/ContrtModal.vue";
  import {ITEMS_PER_PAGE_OPTIONS} from "@/util/config";

  

  const headers = [
    { title: '계약명', key:'contrNm' },
    { title: '계약금액', key:'contrAmount'},
    { title: '지불조건', key:'paymentTerm'},
    { title: '특이사항', key:'specialNote'},
    { title: '계약시작일자', key:'contrStDate'},
    { title: '계약종료일자', key:'contrEndDate'},
  ];
</script>

<script>


import store from "@/store/store";
import axios from "axios";
import {MODAL_MODE} from "@/util/config";
import contrApi from '@/api/contr.js'

const BASE_URL = import.meta.env.VITE_BASE_URL;
const BE_PORT = import.meta.env.VITE_BE_PORT;

export default {
  mounted() {
    this.getContrs(); // 계약 리스트 조회
  },

  computed: {
      store() {
        return store
      }
    },

  data() {
    return {
      search: '',
      contrs: [],
      selected : [],
      popUpValue : false,
    };
  },

  methods: {
    
     async getContrs(){ // 계약 리스트 조회  
     
      this.contrs = await contrApi.contrs();

      console.log(JSON.stringify(this.contrs));
    },

    popUpOpen(event,{item}){
      console.log(item.contrId);

      this.contrs.contrId = item.contrId;

      this.$store.commit("toggleModal", {key: this.contrs.contrId, mode: MODAL_MODE.DETAIL});
      
    },

    pushRegPop: () => {
      store.commit("toggleModal", {key: '', mode: MODAL_MODE.REG});

    },

    // async deleteProject(){ // 프로젝트 삭제
    // console.log(this.selected);
    //   if(this.selected.length <= 0){
    //     alert("삭제할 행을 선택하세요");
    //   }else{
    //    confirm("삭제하시겠습니까?");
    //    const res =  await projectApi.deleteProject(this.selected);
        
    //   }
    // }
  }
};
</script>
