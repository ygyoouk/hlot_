<template>
  <ModalLayout
   @close="this.$emit('close')"
  >
    <div class="modal-title">
      공통코드 관리{{ mode === MODAL_MODE.DETAIL ? '상세'
                : mode === MODAL_MODE.REG ? '등록' : '수정' }}
    </div>

    <div class="modal-content">
      <v-container>
        <v-form ref="form">
          <v-row>
            <v-col>
              <v-select
                  label="코드그룹"
                  :items="codeGroup"
                  item-title="codeGroupNm"
                  item-value="codeGroup"
                  v-model="code.codeGroup"
                  :readonly="mode === MODAL_MODE.DETAIL"
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
        </v-form >
      </v-container>

    </div>
  </ModalLayout>
</template>

<script setup>
import ModalLayout from "@/layouts/ModalLayout.vue";
</script>

<script>
import store from "@/store/store";

const BASE_URL = import.meta.env.VITE_BASE_URL;
const BE_PORT = import.meta.env.VITE_BE_PORT;
import {MODAL_MODE} from "@/util/config";
import commonApi from "@/api/common.js"
import utils from "@/util/validUtil";
import contrApi from '@/api/contr';
import companyApi from '@/api/company';
import topContrApi from '@/api/project';

export default {
  name: "ProjectModal",

  beforeMount(){
    //상세조회
    if(this.mode == 'D'){
        /** 계약 단건 조회*/
        console.log("sssss");
        this.detailContr();
    }else{
      // 코드그룹조회
      this.getCodeGroup();
    }

  },

  mounted(){
    if(this.mode == 'D'){
    }
  },

  data() {
    return {
      visible : false,

      mode: store.getters.getParams.mode,

      key:store.getters.getParams.key,


      code : {
        codeGroup : '',
        codeGroupNm : '',
        code : '',
        codeNm : '',
      },

      params : store.getters.getParams,

      codeGroup :[],
    }
  },

  methods: {
      close:()=>{
        store.commit("toggleModal");
      },

      /**
       *  코드그룹 조회
       * */
      async getCodeGroup() {
       
        this.codeGroup = await commonApi.codeGroup();
        
        console.log(JSON.stringify(this.codeGroup));
      },

      /**
       * 공통코드 저장
      */
      async newCmmCode(){


        const { valid } = await this.$refs.form.validate();
        if(!valid) return false;

        if(!confirm("등록하시겠습니까?")) return false;

          await commonApi.newCmmCode(this.code);
      },


      // 계약 단건조회
      async detailContr(){

        this.contr = await contrApi.contr(this.key);

        this.contrStDate = this.contr.contrStDate; // 계약시작일자
        this.contrEndDate = this.contr.contrEndDate; // 계약종료일자
        
        
      },

      // 모드 변경
      updateMode(){
          this.mode = 'M';
      },

    }
}
</script>

<style>
  @import "@/assets/styles/modal.css";
</style>
