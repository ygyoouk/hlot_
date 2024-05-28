<template>
  <ContrModal v-if="bContrModal"
   @close="bContrModal = false"/>

  <v-card class="table-container_mt">
    <div class="table-title_mt">
      공통코드 관리
    </div>

    <v-card-title>

        <v-row>

        <v-col cols="3">
          <v-text-field
            v-model="searchParam.codeGroupNm"
            label="코드그룹"
            density="comfortable"
            @keyup="enterKey()"
          ></v-text-field>
        </v-col>

        <v-col>
          <v-text-field
            density="comfortable"
            label="코드명"
            @keyup="enterKey()"
            v-model="searchParam.codeNm"
          >
          </v-text-field>
        </v-col>


        <v-col cols="auto">
          <v-btn color="green" @keyup="enterKey()" @click="getComCd()">조회</v-btn>
        </v-col>

        <v-col cols="auto">
          <v-btn color="red" @keyup="enterKey()" @click="clearSearchParam()">초기화</v-btn>
        </v-col>

      </v-row>

    </v-card-title>

    <div class="table-btn-list">
      <v-btn color="#5865f2" v-if="false" @click="pushRegPop">등록</v-btn>

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
      select-strategy="page"
      :items-per-page-options="ITEMS_PER_PAGE_OPTIONS"
      class="elevation-1 table-list_mt"
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
    { title: '코드명', key:'codeNm' },
    { title: '코드', key:'code'},
    { title: '코드그룹명', key:'codeGroupNm'},
  ];
</script>

<script>


import store from "@/store/store";
import axios from "axios";
import {MODAL_MODE} from "@/util/config";
import commApi from '@/api/common.js'
import utils from "@/util/validUtil";

const BASE_URL = import.meta.env.VITE_BASE_URL;
const BE_PORT = import.meta.env.VITE_BE_PORT;

export default {
  mounted() {
    // this.getContrs(); // 계약 리스트 조회
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
      bComCdModal : false,
      searchParam: {
        codeGroupNm : '',
        codeNm : '',
      },
    };
  },

  methods: {
    
    // 공통코드 조회
     async getComCd(){ 

      this.contrs = await commApi.cmmCodeComp();
      this.searchParam = {};
    },

    popUpOpen(event,{item}){
      this.bComCdModal = !this.bComCdModal;

      this.contrs.contrId = item.contrId;

      this.$store.commit('setModalParams', {key: this.contrs.contrId, mode: MODAL_MODE.DETAIL});

      // this.$store.commit("toggleModal", {key: this.contrs.contrId, mode: MODAL_MODE.DETAIL});
    },

    pushRegPop(){
      
      store.commit('setModalParams', {key: '', mode: MODAL_MODE.REG});
      
      this.bContrModal = !this.bContrModal;
    },

    /**
     * enter키 클릭시 조회
    */
    enterKey(){
      if(window.event.keyCode == 13){
        this.getComCd(this.searchParam);
      }
    },

    /**
     * 검색조건 초기화
    */
    clearSearchParam(){
      this.searchParam = {};
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
