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

                  variant="outlined"
                  color="primary"
                ></v-select>

              </v-col>
              <v-col>
                <v-text-field
                  v-model="company.compNm"
                  label="업체명"
                  :readonly="mode === MODAL_MODE.DETAIL"
                  :rules="[validUtil.required]"

                  variant="outlined"
                  color="primary"
                />
              </v-col>
              <v-col>
                <v-text-field
                  v-model="company.compBussRegnum"
                  label="사업자등록번호"
                  :readonly="mode === MODAL_MODE.DETAIL"
                  :rules="[validUtil.required, validUtil.number]"

                  variant="outlined"
                  color="primary"
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

                  variant="outlined"
                  color="primary"
                />
              </v-col>
              <v-col>
                <v-text-field
                  :readonly="mode === MODAL_MODE.DETAIL"
                  v-model="company.compTel"
                  :rules="[validUtil.number]"
                  label="전화번호"

                  variant="outlined"
                  color="primary"
                />
              </v-col>
              <v-col>
                <v-text-field
                  :readonly="mode === MODAL_MODE.DETAIL"
                  v-model="company.compAddr"
                  label="업체주소"
                  @click="execDaumPostcode()"
                  :rules="[validUtil.required]"

                  variant="outlined"
                  color="primary"
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
                    <tr v-for="(manager, index) in company.companyManagers">
                      <td style="width: 200px;">
                        <v-select
                          density="compact"
                          :items="compMngerDivs"
                          item-title="codeNm"
                          item-value="code"
                          v-model="manager.compMngerDiv"
                          :readonly="mode === MODAL_MODE.DETAIL"
                          :rules="[validUtil.required]"

                          variant="outlined"
                          color="primary"
                        ></v-select>
                      </td>
                      <td style="width: 200px;">
                        <v-text-field
                          v-model="manager.compMngerNm"
                          density="compact"
                          :readonly="mode === MODAL_MODE.DETAIL"
                          :rules="[validUtil.required]"

                          variant="outlined"
                          color="primary"
                        />
                      </td>
                      <td style="width: 400px;">
                        <v-text-field
                          v-model="manager.compMngerTel"
                          density="compact"
                          :readonly="mode === MODAL_MODE.DETAIL"
                          :rules="[validUtil.required, validUtil.number]"

                          variant="outlined"
                          color="primary"
                        />
                      </td>
                      <td style="width: 400px;">
                        <v-text-field
                          v-model="manager.compMngerEmail"
                          density="compact"
                          :rules="[]"
                          :readonly="mode === MODAL_MODE.DETAIL"

                          variant="outlined"
                          color="primary"
                        />
                      </td>
                      <td style="text-align: center; width: 30px">
                        <v-btn
                          v-if="mode !== MODAL_MODE.DETAIL"
                          color="red"
                          density="compact"
                          @click="deleteManager(manager, index)"
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
      },

      bAddressModal : false,

      postcode: "",
      address: "",
      extraAddress: "",
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
    /* company_manager 삭제 */
    async deleteManager(manager, index) {
      if(!confirm("삭제 하시겠습니까?")) return false;

      if(!manager.compMngerId) {
        this.company.companyManagers.splice(index, 1);
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

    /* company_manager modal 닫기 */
    closeManagerModal() {
      this.companyManager = {};
      this.managerModal = false;
    },


    execDaumPostcode() {
      new window.daum.Postcode({
        oncomplete: (data) => {
          console.log(data);
          if (this.extraAddress !== "") {
            this.extraAddress = "";
          }
          if (data.userSelectedType === "R") {
            // 사용자가 도로명 주소를 선택했을 경우
            this.address = data.roadAddress;
            this.company.compAddr = data.roadAddress;
          } else {
            // 사용자가 지번 주소를 선택했을 경우(J)
            this.address = data.jibunAddress;
            this.company.compAddr = data.jibunAddress;
          }

        },
      }).open();
    },


  }
}
</script>

<style scoped>
  @import "@/assets/styles/modal.css";
  @import "@/assets/styles/customTable.css";

</style>
