<template>
  

  <v-card class="table-container_mt">
    <div class="table-title_mt">
      공통코드 관리
    </div>

    <v-card-title>

      <v-row>

        <v-col cols="3">
          <v-select
              label="코드그룹"
              :items="codeGroup"
              item-title="codeGroupNm"
              item-value="codeGroup"
              v-model="searchParam.codeGroupNm"
              ></v-select>
        </v-col>

        <v-col cols="auto">
          <v-btn color="green" @keyup="enterKey()" @click="getComCd()">조회</v-btn>
        </v-col>

        <!-- <v-col cols="auto">
          <v-btn color="red" @keyup="enterKey()" @click="clearSearchParam()">초기화</v-btn>
        </v-col> -->

        <v-col cols="auto">
          <v-btn color="#5865f2"  @click="pushRegPop()">등록</v-btn>
        </v-col>

       
      </v-row>

      <v-form ref="form">
      <v-row v-if="visible">
        <v-col>
           <v-select
              label="코드그룹"
              :items="codeGroup"
              item-title="codeGroupNm"
              item-value="codeGroup"
              v-model="code.codeGroup"
              :rules="[utils.required]"
              ></v-select>
        </v-col>

        <v-col>
          <v-text-field 
              label="코드명"
              :rules="[utils.required]"
              v-model="code.codeNm">
          </v-text-field>
        </v-col>

        <v-col>
          <v-btn
            color="blue"
            v-if="mode !== 'D'"
            @click="newCmmCode"
          >저장</v-btn>
        </v-col>

      </v-row>
      </v-form>
    </v-card-title>

    <v-data-table
      :headers="headers"
      :items="cmmCode"
      item-value="code"
      v-model="selected"
      select-strategy="page"
      :items-per-page-options="ITEMS_PER_PAGE_OPTIONS"
      class="elevation-1 table-list_mt"
      @click:row="popUpOpen"
      
    >

    <template v-slot:item.actions="{ item }">
      <v-btn color="red"
      @click="deleteCmmCode(item.code)"
      >
        삭제
      </v-btn>
    </template>

    </v-data-table>

  </v-card>


</template>

<script setup>

  import CmmCdModal from "@/components/modal/CmmCdModal.vue";
  import {ITEMS_PER_PAGE_OPTIONS} from "@/util/config";



  const headers = [
    { title: '코드명', key:'codeNm' },
    { title: '코드', key:'code'},
    { title: '코드그룹명', key:'codeGroupNm'},
    {title: '', key: 'actions'},
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

  beforeMount(){
    // 코드그룹조회
    console.log("$$$$$$$$$$$$$$$$$$$$");
    this.getCodeGroup();
  },

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
      cmmCode: [],
      selected : [],
      bComCdModal : false,
      visible : false,
      searchParam: {
        codeGroupNm : '',
        codeNm : '',
      },

      codeGroup :[],

      code : {
        codeGroup : '',
        codeGroupNm : '',
        code : '',
        codeNm : '',
      },
    };
  },

  methods: {
    
    // 공통코드 조회
     async getComCd(){ 

      this.cmmCode = await commApi.cmmCodeComp(this.searchParam.codeGroupNm);
      this.searchParam = {};
    },

      /**
       *  코드그룹 조회
       * */
      async getCodeGroup() {
       
        this.codeGroup = await commApi.codeGroup();
        
        console.log(JSON.stringify(this.codeGroup));
      },

    popUpOpen(event,{item}){
      console.log(item.code)

      this.bComCdModal = !this.bComCdModal;

      this.$store.commit('setModalParams', {key: '', mode: MODAL_MODE.DETAIL});

      // this.$store.commit("toggleModal", {key: this.contrs.contrId, mode: MODAL_MODE.DETAIL});
    },

    pushRegPop(){
      
      store.commit('setModalParams', {key: '', mode: MODAL_MODE.REG});
      
      this.visible = !this.visible;
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

     /**
       * 공통코드 저장
      */
      async newCmmCode(){
        const { valid } = await this.$refs.form.validate();
        if(!valid) return false;

        if(!confirm("등록하시겠습니까?")) return false;

        await commApi.newCmmCode(this.code);

        this.visible =  !this.visible; 
      },

      /*코드 삭제*/
      async deleteCmmCode(code){
        console.log("code ==========>" + code);
        if(!confirm("삭제하시겠습니까?")) return false;

        await commApi.deleteCmmCode(code);
      }
  }
};
</script>
