<template>
  <ProjectModal
  v-if="bProjectModal"
   @close="bProjectModal = false"/>

  <CompnaySearchModal
  v-if="bCompanySearchModal"
  :compDiv="compDiv"
  @select="selectNm"
  @close="bCompanySearchModal = !bCompanySearchModal"
  />

  <v-card class="table-container_mt">
    <div class="table-title_mt">
      원계약 관리
    </div>

    <v-card-title>
      <v-row>
        <v-col>
          <v-text-field
            v-model="searchParam.topContrNm"
            label="원계약명"
            density="comfortable"
            @keyup="enterKey()"
          ></v-text-field>
        </v-col>
        <v-col>
            <v-select
            label="원계약구분"
            density="comfortable"
            @keyup="enterKey()"
            :items="['유지관리', '구축', '개발']"
            v-model="searchParam.topContrDiv"
            >
            </v-select>
        </v-col>
        <v-col>
          <v-text-field
            v-model="searchParam.clientComp"
            density="comfortable"
            readonly="readonly"
            @keyup="enterKey()"
            label="발주처"
            append-inner-icon="mdi-magnify"
            @click:append-inner="compSearchPopUp('COMP03')"
          ></v-text-field>
        </v-col>
      </v-row>

      <v-row>
        <v-col>
          <v-text-field 
          label="계약시작일자"
          type="date"
          density="comfortable"
          @keyup="enterKey()"
          v-model="searchParam.topContrStDate"
          >
          </v-text-field>
        </v-col>
        
        <v-col>
          <v-text-field 
          label="계약종료일자"
          type="date"
          density="comfortable"
          @keyup="enterKey()"
          v-model="searchParam.topContrEndDate"
          >
          </v-text-field>
        </v-col>

        <v-col>
          <v-text-field
            v-model="searchParam.prodNm"
            density="comfortable"
            @keyup="enterKey()"
            prepend-inner-icon="mdi-magnify"
            label="품명"
          ></v-text-field>
        </v-col>

        <v-col>
          <v-text-field
            v-model="searchParam.demandInstNm"
            append-inner-icon="mdi-magnify"
            density="comfortable"
            readonly="readonly"
            @keyup="enterKey()"
            @click:append-inner="compSearchPopUp('COMP02')"
            label="수요기관명"
          ></v-text-field>
        </v-col>

        <v-col cols="auto">
          <v-btn color="green"  @keyup="enterKey()" @click="searchTopContr()">조회</v-btn>
        </v-col>

        <v-col cols="auto">
          <v-btn color="red"  @keyup="enterKey()" @click="clearSearchParam()">초기화</v-btn>
        </v-col>

      </v-row>
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

  import ProjectModal from "@/components/modal/ProjectModal.vue";
  import CompnaySearchModal from "@/components/modal/search/CompanySearch.vue"
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
    { title: '수요기관명',  key:'demandInst'},
  ];
</script>

<script>


import store from "@/store/store";
import axios from "axios";
import {MODAL_MODE} from "@/util/config";
import projectApi from '@/api/project.js'
import utils from "@/util/validUtil";

const BASE_URL = import.meta.env.VITE_BASE_URL;
const BE_PORT = import.meta.env.VITE_BE_PORT;

export default {
  mounted() {
    // this.projects = api.projectSampleData();

    // this.getTopContrs(); // 프로젝트리스트 조회


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
      bProjectModal: false,
      bCompanySearchModal : false,
      compDiv : '',
      searchParam : {

        topContrNm : '',
        topContrDiv : '',
        clientComp : '',
        clientCompId : '',
        topContrStDate : '',
        topContrEndDate : '',
        prodNm : '',
        demandInstNm : '',
        demandInstId : '',
      },

    };
  },
  methods: {

    /**
     *  원계약 조회
    */
    searchTopContr(){

      this.searchParam.topContrStDate = utils.saveDate(this.searchParam.topContrStDate);
      this.searchParam.topContrEndDate = utils.saveDate(this.searchParam.topContrEndDate);

      console.log("searchParam" + JSON.stringify(this.searchParam));

      this.getTopContrs(this.searchParam); // 프로젝트리스트 조회
    },

    enterKey(){
      if(window.event.keyCode == 13){
        console.log("enter!!!!!");
        this.searchTopContr();
      }
    },


    popUpOpen(event,{item}){
      console.log(item.topContrId);

      this.topContrs.topContrId = item.topContrId;

      this.store.commit('setModalParams', {key: this.topContrs.topContrId, mode: MODAL_MODE.DETAIL});

      this.bProjectModal = !this.bProjectModal;
      // this.$store.commit("toggleModal", {key: this.topContrs.topContrId, mode: MODAL_MODE.DETAIL});

    },

    pushRegPop(){
      store.commit('setModalParams', {key: '', mode: MODAL_MODE.REG});

      this.bProjectModal = !this.bProjectModal;
    },

    compSearchPopUp(compDiv){
      console.log(compDiv)
      this.compDiv = compDiv; // 업체구분
      this.bCompanySearchModal =!this.bCompanySearchModal;
    },

    async getTopContrs(searchParam){ // 원계약 리스트 조회
      this.topContrs = await projectApi.projects(searchParam);

      // 조회조건 초기화
      // this.searchParam = {};
    },

    /**
     * 검색조건 초기화
    */
    clearSearchParam(){
      this.searchParam = {};
    },

    /**
     *발주처,수요기관 검색한 내용 가져오기
    */
    selectNm(obj){
      const compDiv = obj.div;

      // 발주처 :  COMP03 / 수요기관 :  COMP02
      if(compDiv === "COMP03"){
        this.searchParam.clientComp   = obj.nm;
        this.searchParam.clientCompId = obj.id;
      }else{
        this.searchParam.demandInstNm = obj.nm;
        this.searchParam.demandInstId = obj.id;
      }
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
