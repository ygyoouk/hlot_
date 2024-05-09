<template>
  <ProjectModal v-if="store.getters.isOpenModal"/>

  <v-card class="table-container_mt">
    <div class="table-title_mt">
      원계약 관리
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
      :items="topContrs"
      item-value="topContrId"
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
  
  import ProjectModal from "@/components/modal/ProjectModal.vue";
  import {ITEMS_PER_PAGE_OPTIONS} from "@/util/config";

  

  const headers = [
    { title: '원계약명', key:'topContrNm' },
    { title: '원계약구분', key:'topContrDiv'},
    { title: '발주처', key:'clientComp'},
    { title: '원계약일자', key:'topContrDate'},
    { title: '계약시작일자',  key:'contrStDate'},
    { title: '계약종료일자',  key:'contrEndDate'},
    { title: '품명',  key:'prodNm'},
    { title: '계약금액',  key:'contrAmount'},
    { title: '납품기한',  key:'deliveryDeadline'},
    { title: '수요기관명',  key:'demandInstNm'},
  ];
</script>

<script>


import store from "@/store/store";
import axios from "axios";
import {MODAL_MODE} from "@/util/config";
import projectApi from '@/api/project.js'

const BASE_URL = import.meta.env.VITE_BASE_URL;
const BE_PORT = import.meta.env.VITE_BE_PORT;

export default {
  mounted() {
    // this.projects = api.projectSampleData();

    this.getTopContrs(); // 프로젝트리스트 조회


  },
  computed: {
      store() {
        return store
      }
    },
  data() {
    return {
      search: '',
      topContrs: [],
      selected : [],
      popUpValue : false,
    };
  },
  methods: {
    
    popUpOpen(event,{item}){
      console.log(item.topContrId);

      this.topContrs.topContrId = item.topContrId;

      this.$store.commit("toggleModal", {key: this.topContrs.topContrId, mode: MODAL_MODE.DETAIL});
      
    },

    pushRegPop: () => {
      store.commit("toggleModal", {key: '', mode: MODAL_MODE.REG});

    },

    async getTopContrs(){ // 원계약 리스트 조회  
      this.topContrs = await projectApi.projects();
    },


    async deleteProject(){ // 프로젝트 삭제
    console.log(this.selected);
      if(this.selected.length <= 0){
        alert("삭제할 행을 선택하세요");
      }else{
       confirm("삭제하시겠습니까?");
       const res =  await projectApi.deleteProject(this.selected);
        
      }
    }
  }
};
</script>
