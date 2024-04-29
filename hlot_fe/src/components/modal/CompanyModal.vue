<template>
  <ModalLayout>
    <div class="modal-title">
      업체관리 {{ mode === MODAL_MODE.DETAIL ? '상세'
                : mode === MODAL_MODE.REG ? '등록' : '수정' }}
    </div>

    <div class="modal-content">
      <v-container>
        <v-row>
          <v-col>
            <v-text-field
              v-model="company.companyName"
              label="업체명"
              :readonly="mode === MODAL_MODE.DETAIL"
            />
          </v-col>
        </v-row>
        <v-row>
          <v-col>
            <v-text-field
              :readonly="mode === MODAL_MODE.DETAIL"
              v-model="company.businessRegistNumb"
              label="사업자번호"
            />
          </v-col>
          <v-col>
            <v-text-field
              :readonly="mode === MODAL_MODE.DETAIL"
              v-model="company.companyTel"
              label="전화번호"
            />
          </v-col>
        </v-row>
        <v-row>
          <v-col>
            <v-textarea
              :readonly="mode === MODAL_MODE.DETAIL"
              filled
              label="비고"
              v-model="company.remark"
            />
          </v-col>
        </v-row>
        <v-row>
          <v-col>
            <hr><br>
          </v-col>
        </v-row>
        <v-row style="margin-bottom: 10px">
          <v-col style="font-size: 20px;">
            업체담당자
          </v-col>
          <v-col style="text-align: right">
            <v-btn
              v-if="mode !== MODAL_MODE.DETAIL"
              @click="openManagerModal()"
              color="green">담당자 추가</v-btn>
          </v-col>
        </v-row>
        <v-row>
          <v-col>
            <table class="custom-table_mt">
              <thead>
                <tr>
                  <th>담당자명</th>
                  <th>전화번호</th>
                  <th>비　　고</th>
                  <th>등록일자</th>
                </tr>
              </thead>
              <tbody>
                  <tr v-for="manager in company.companyManagers" @click="openManagerModal(manager)">
                    <td>{{ manager.companyManagerName }}</td>
                    <td>{{ manager.companyManagerTel }}</td>
                    <td>{{ manager.remark }}</td>
                    <td>{{ manager.registDate }}</td>
                  </tr>
              </tbody>
            </table>
          </v-col>
        </v-row>
        <v-row>
          <div class="modal-btn-list">
            <v-btn
              @click="mode = MODAL_MODE.MOD"
              v-if="mode === MODAL_MODE.DETAIL"
              color="blue"
            >수정</v-btn>
            　
            <v-btn
              v-if="mode !== MODAL_MODE.DETAIL"
              color="green"
              @click="newCompany"
            >저장</v-btn>
            　
            <v-btn
              v-if="mode !== MODAL_MODE.DETAIL"
              color="red"
              @click="deleteCompany"
            >삭제</v-btn>
          </div>
        </v-row>
      </v-container>
    </div>
  </ModalLayout>

  <!-- 업체 담당자 -->
  <div v-if="managerModal" class="child-modal-overlay">
    <div v-if="managerModal"  class="child-modal">

      <div style="text-align: right">
        <div @click="closeManagerModal" class="close"></div>
      </div>
      <v-row>
        <v-col>
          <v-text-field
            v-model="companyManager.companyManagerName"
            label="업체담당자"
          />
        </v-col>
        <v-col>
          <v-text-field
            v-model="companyManager.companyManagerTel"
            label="전화번호"
          />
        </v-col>
      </v-row>
      <v-row>
        <v-col>
          <v-text-field
            v-model="companyManager.remark"
            label="비고"
            filled
          />
        </v-col>
      </v-row>
      <div class="modal-btn-list">
        <v-btn
          color="green"
          @click="addManager"
        >저장</v-btn>
        　
        <v-btn
          v-if="companyManager.companyId"
          color="red"
          @click="deleteManager"
        >삭제</v-btn>
      </div>
    </div>
  </div>
</template>

<script setup>
import ModalLayout from "@/layouts/ModalLayout.vue";
import {MODAL_MODE} from "@/util/config";
</script>

<script>
import companyApi from '@/api/company.js'
import store from "@/store/store";
import validUtil from "@/util/validUtil";
export default {
  name: "CompanyModal",
  beforeMount() {
    if(!validUtil.isNull(this.key)) this.getCompany();
  },
  data() {
    return {
      managerModal: false,

      mode: store.getters.getParams.mode,
      key:store.getters.getParams.key,

      company: {  // 업체
        companyId: '',          // 업체ID
        companyName: '',        // 업체명
        businessRegistNumb: '', // 사업자번호
        companyTel: '',         // 업체번호
        remark: '',             // 비고
        registUserName: '관리자',// 등록자

        companyManagers: [] // 업체담당자
      },

      companyManager: {   // 업체담당자
        companyManagerName: '', // 업체담당자명
        companyManagerTel: '',  // 업체담당자번호
        remark: '',             // 비고
        registDate : ''         // 등록일자
      }
    }
  },
  methods : {
    /* company 상세조회 */
    async getCompany(){
      this.company = await companyApi.company(this.key);
    },
    /* company 등록 */
    async newCompany() {
      await companyApi.newCompany(this.company);
    },
    /* company 삭제 */
    async deleteCompany() {
      await companyApi.deleteCompany(this.key);
    },
    /* company_manager 추가 */
    addManager() {
      this.companyManager.companyId = this.company.companyId;
      if(companyApi.newCompanyManager(this.companyManager) > 0){
        this.getCompany();
        this.managerModal = false;
      }
    },
    /* company_manager 삭제 */
    async deleteManager() {
      if(!this.companyManager.companyManagerId) {
        return false;
      }

      if( await companyApi.deleteCompanyManager(this.companyManager.companyManagerId) > 0 ) {
        this.companyManager = {};
        this.managerModal = false;
        await this.getCompany();
      }
    },
    /* company_manager modal 열기 */
    openManagerModal(manager = {}) {
      this.companyManager = manager;
      this.managerModal = true;
    },
    /* company_manager modal 닫기 */
    closeManagerModal() {
      this.companyManager = {};
      this.managerModal = false;
    }
  }
}
</script>

<style scoped>
  @import "@/assets/styles/modal.css";
  @import "@/assets/styles/customTable.css";

  .child-modal{
    width: 500px;
    height: 320px;
  }
</style>
