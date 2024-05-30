<template>
  <ModalLayout
    @close="$emit('close')"
  >
    <div class="modal-title">
      업체관리 {{ mode === MODAL_MODE.DETAIL ? '상세'
                : mode === MODAL_MODE.REG ? '등록' : '수정' }}
    </div>

    <div class="modal-content">
      <v-container>
        <v-form ref="form">
          <v-row>
              <v-col>
                <v-select
                  label="업체구분"
                  :items="compDivs"
                  item-title="codeNm"
                  item-value="code"
                  v-model="company.compDiv"
                  :readonly="mode === MODAL_MODE.DETAIL"
                  :rules="[validUtil.required]"
                ></v-select>

              </v-col>
              <v-col>
                <v-text-field
                  v-model="company.compNm"
                  label="업체명"
                  :readonly="mode === MODAL_MODE.DETAIL"
                  :rules="[validUtil.required]"
                />
              </v-col>
              <v-col>
                <v-text-field
                  v-model="company.compBussRegnum"
                  label="사업자등록번호"
                  :readonly="mode === MODAL_MODE.DETAIL"
                  :rules="[validUtil.required, validUtil.number]"
                />
              </v-col>
            </v-row>
            <v-row>
              <v-col>
                <v-text-field
                  :readonly="mode === MODAL_MODE.DETAIL"
                  v-model="company.compCeoNm"
                  :rules="[]"
                  label="업체대표"
                />
              </v-col>
              <v-col>
                <v-text-field
                  :readonly="mode === MODAL_MODE.DETAIL"
                  v-model="company.compTel"
                  :rules="[validUtil.required, validUtil.number]"
                  label="전화번호"
                />
              </v-col>
              <v-col>
                <v-text-field
                  :readonly="mode === MODAL_MODE.DETAIL"
                  v-model="company.compAddr"
                  label="업체주소"
                  :rules="[validUtil.required]"
                />
              </v-col>
          </v-row>
        </v-form>
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
              @click="addCompanyManager()"
              color="green">담당자 추가</v-btn>
          </v-col>
        </v-row>
        <v-row>
          <v-col>
            <v-form ref="managerForm">
              <table class="custom-table_mt">
                <thead>
                  <tr>
                    <th>담당자구분</th>
                    <th>담당자명</th>
                    <th>전화번호</th>
                    <th>이메일</th>
                    <th>-</th>
                  </tr>
                </thead>
                <tbody>
                    <tr v-for="manager in company.companyManagers">
                      <td style="width: 200px;">
                        <v-select
                          density="compact"
                          :items="compMngerDivs"
                          item-title="codeNm"
                          item-value="code"
                          v-model="manager.compMngerDiv"
                          :readonly="mode === MODAL_MODE.DETAIL"
                          :rules="[validUtil.required]"
                        ></v-select>
                      </td>
                      <td style="width: 200px;">
                        <v-text-field
                          v-model="manager.compMngerNm"
                          density="compact"
                          :readonly="mode === MODAL_MODE.DETAIL"
                          :rules="[validUtil.required]"
                        />
                      </td>
                      <td style="width: 400px;">
                        <v-text-field
                          v-model="manager.compMngerTel"
                          density="compact"
                          :readonly="mode === MODAL_MODE.DETAIL"
                          :rules="[validUtil.required, validUtil.number]"
                        />
                      </td>
                      <td style="width: 400px;">
                        <v-text-field
                          v-model="manager.compMngerEmail"
                          density="compact"
                          :rules="[]"
                          :readonly="mode === MODAL_MODE.DETAIL"
                        />
                      </td>
                      <td style="text-align: center; width: 30px">
                        <v-btn
                          color="red"
                          density="compact"
                          @click="deleteManager(manager)"
                        >행 삭제</v-btn>
                      </td>
                    </tr>
                </tbody>
              </table>
            </v-form>
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
  <!-- 업체담당자 모달 제거
  <div v-if="managerModal" class="child-modal-overlay">
    <div v-if="managerModal"  class="child-modal">

      <div style="text-align: right">
        <div @click="closeManagerModal" class="close"></div>
      </div>
      <v-form ref="managerForm">
        <v-row>
          <v-col>
            <v-text-field
              v-model="companyManager.compMngerNm"
              label="업체담당자 명"
              :rules="[validUtil.required]"
            />
          </v-col>
          <v-col>
            <v-text-field
              v-model="companyManager.compMngerTel"
              label="전화번호"
              :rules="[validUtil.required, validUtil.number]"
            />
          </v-col>
        </v-row>
        <v-row>
          <v-col>
            <v-text-field
              v-model="companyManager.compMngerEmail"
              label="이메일"
              :rules="[validUtil.email]"
            />
          </v-col>
        </v-row>
      </v-form>
      <div class="modal-btn-list">
        <v-btn
          color="green"
          @click="addManager"
        >저장</v-btn>
        　
        <v-btn
          color="red"
          @click="deleteManager"
        >삭제</v-btn>
      </div>
    </div>
  </div>
  -->
</template>

<script setup>
import ModalLayout from "@/layouts/ModalLayout.vue";
import {MODAL_MODE} from "@/util/config";
import validUtil from "@/util/validUtil";
</script>

<script>
import companyApi from '@/api/company.js'
import validUtil from "@/util/validUtil";
import commonApi from "@/api/common";

export default {
  name: "CompanyModal",
  props: ['mode', 'companyModalKey'],
  async beforeMount() {
    this.compDivs = await commonApi.cmmCodeComp('COMP');
    this.compMngerDivs = await commonApi.cmmCodeComp('COMPM');
    if(!validUtil.isNull(this.$props.companyModalKey)) await this.getCompany();
  },
  data() {
    return {
      compDivs: [],
      compMngerDivs: [],
      managerModal: false,

      company: {  // 업체
        compId: '',          // 업체ID
        compNm: '',        // 업체명
        compBussRegnum: '', // 사업자등록번호
        compCeoNm: '',       // 업체대표명
        compTel: '',         // 업체번호
        compAddr: '',         // 업체주소
        compDiv: '',         // 업체구분

        companyManagers: [] // 업체담당자
      },

      companyManager: {   // 업체담당자
        compMngerNm: '', // 업체담당자명
        compMngerTel: '',  // 업체담당자번호
        compMngerEmail: '', // 업체담당자이메일
        compMngerDiv: '', // 업체담당자구분
        registUserName: '',
      }
    }
  },
  methods : {
    /* company 상세조회 */
    async getCompany(){
      this.company = await companyApi.company(this.companyModalKey);
    },
    /* company 등록 */
    async newCompany() {

      const { valid } = await this.$refs.form.validate();
      if(!valid) return false;

      if(this.company.companyManagers.length > 0){
        const mngerValid = (await this.$refs.managerForm.validate()).valid;
        if(!mngerValid) return false;
      }

      if(!confirm("등록 하시겠습니까?")) return false;

      await companyApi.newCompany(this.company);
      this.$emit('update');
      await this.getCompany();
      // this.$emit('close');

    },
    /* company 삭제 */
    async deleteCompany() {
      if(!confirm("삭제 하시겠습니까?")) return false;

      await companyApi.deleteCompany(this.companyModalKey);
      this.$emit('update');
      this.$emit('close');
    },
    /* company_manager 추가 */
    async addManager() {
      const { valid } = await this.$refs.managerForm.validate();
      if(!valid) return false;

      if(validUtil.isNull(this.companyManager.compMngerNm)) {
        alert('담당자 명을 입력해주세요.');
        return false;
      }

      if(!confirm("등록 하시겠습니까?")) return false;

      this.companyManager.compId = this.company.compId;

      if(validUtil.isNull(this.companyModalKey)){
        this.company.companyManagers.push(this.companyManager);
      } else {
        await companyApi.newCompanyManager(this.companyManager);
        await this.getCompany();
      }
      this.closeManagerModal();
    },
    /* company_manager 삭제 */
    async deleteManager(manager = {}) {
      if(!confirm("삭제 하시겠습니까?")) return false;

      if(!manager.compMngerId) {
        this.company.companyManagers.forEach((v, i)=> {
          if(v.compMngerNm === manager.compMngerNm
            && v.compMngerTel === manager.compMngerTel
            && v.compMngerEmail === manager.compMngerEmail) {
            this.company.companyManagers.splice(i, 1);
          }
        });
      } else {
        await companyApi.deleteCompanyManager(manager.compMngerId);
        await this.getCompany();
      }
      this.closeManagerModal();
    },
    /* company_manager 행추가 */
    addCompanyManager(){
      this.company.companyManagers.push({});
    },
    /* company_manager modal 열기 */
    openManagerModal(manager = {}) {
      this.companyManager = {...manager};
      this.managerModal = true;
    },
    /* company_manager modal 닫기 */
    closeManagerModal() {
      this.companyManager = {};
      this.managerModal = false;
    },



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
