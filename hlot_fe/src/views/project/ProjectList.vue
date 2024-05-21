<template>
  <ProjectModal v-if="store.getters.isOpenModal"/>

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
            label="계약구분"
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
            @keyup="enterKey()"
            prepend-inner-icon="mdi-magnify"
            label="발주처"
          ></v-text-field>
        </v-col>
      </v-row>
      <v-row>
        <v-col>
          <label> 원계약 기간 : </label>
          <input type="date" @keyup="enterKey()" id="strDate" v-model="searchParam.topContrStDate"/>  ~
          <input type="date" @keyup="enterKey()" id="endDate" v-model="searchParam.topContrEndDate"/>
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
            prepend-inner-icon="mdi-magnify"
            density="comfortable"
            @keyup="enterKey()"
            label="수요기관명"
          ></v-text-field>
        </v-col>

        <v-col cols="auto">
          <v-btn color="green"  @keyup="enterKey()" @click="searchTopContr()">조회</v-btn>
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
      searchParam : {
        topContrNm : '',
        topContrDiv : '',
        clientComp : '',
        topContrStDate : '',
        topContrEndDate : '',
        prodNm : '',
        demandInstNm : '',
      },
      popUpValue : false,
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

      this.$store.commit("toggleModal", {key: this.topContrs.topContrId, mode: MODAL_MODE.DETAIL});

    },

    pushRegPop: () => {
      store.commit("toggleModal", {key: '', mode: MODAL_MODE.REG});

    },

    async getTopContrs(searchParam){ // 원계약 리스트 조회

      this.topContrs = await projectApi.projects(searchParam);
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
