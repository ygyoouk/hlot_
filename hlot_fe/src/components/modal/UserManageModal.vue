<template>
  <ModalLayout
    @close="$emit('close')"
  >
    <div class="modal-title">
      사용자 관리
    </div>
    <div class="modal-content">
      <v-form ref="form">

        <v-text-field
          v-model="user.userId"
          label="ID"
          variant="outlined"
          color="primary"
          :readonly="mode !== MODAL_MODE.REG"
          :rules="[validation.bIdDupCheck]"

          append-inner-icon="mdi-magnify"
          @click:append-inner="idDupCheck"
          @update:modelValue="validation.bIdDupCheck = '돋보기버튼을 클릭하여 ID 중복체크를 진행해주세요.'"
        />

        <v-text-field
          v-model="user.userName"
          label="이름"
          variant="outlined"
          color="primary"
          :rules="[validUtil.required]"
        />

        <v-text-field
          v-model="user.userPassword"
          label="비밀번호"
          variant="outlined"
          color="primary"
          :rules="[validUtil.required]"
          type="password"
          @update:modelValue="validation.bPasswordChk = '돋보기버튼을 클릭하여 비밀번호확인을 진행해주세요.'"
        />

        <v-text-field
          v-model="user.userPasswordChk"
          label="비밀번호확인"
          variant="outlined"
          color="primary"
          type="password"
          :rules="[validUtil.required, validation.bPasswordChk]"

          append-inner-icon="mdi-magnify"
          @click:append-inner="passwordChk"
          @update:modelValue="validation.bPasswordChk = '돋보기버튼을 클릭하여 비밀번호확인을 진행해주세요.'"
        />

        <v-select
          v-model="user.userAuth"
          :items="userAuthDivs"
          item-title="codeNm"
          item-value="code"
          label="사용자권한"
          variant="outlined"
          color="primary"
          :rules="[validUtil.required]"
        />
      </v-form>

      <div class="modal-btn-list">
        <v-btn
          color="green"
          @click="newUser"
        >저장</v-btn>
        　
        <v-btn
          v-if="mode !== MODAL_MODE.REG"
          color="red"
          @click="deleteUser"
        >삭제</v-btn>
      </div>
    </div>
  </ModalLayout>
</template>

<script setup>
import ModalLayout from "@/layouts/ModalLayout.vue";
import {onBeforeMount, reactive, ref} from "vue";
import userManageApi from "@/api/userManage";
import validUtil from "@/util/validUtil";
import {MODAL_MODE} from "@/util/config";
import commonApi from "@/api/common";

const props = defineProps(['mode', 'userId']);
const emits = defineEmits(['close']);
onBeforeMount(() => {
  getUserDivs();

  if(!validUtil.isNull(props.userId)){
    getUser();
    validation.bIdDupCheck = true;
  }
})

const userAuthDivs = ref([]);
const getUserDivs = async () => {
  userAuthDivs.value = await commonApi.cmmCodeComp('USERA');
}

/* USER CRUD */
let user = ref({});
const getUser = async () => { // USER 조회
  user.value = await userManageApi.user(props.userId);
}

const form = ref(null);
const newUser = async () => { // USER 등록
  const { valid } = await form.value.validate();
  if(!valid) return false;

  if(!confirm("등록 하시겠습니까?")) return false;

  await userManageApi.newUser(user.value);
  emits('close');
}

const deleteUser = async () => { // USER 삭제
  if(!confirm("삭제 하시겠습니까?")) return false;

  await userManageApi.deleteUser(user.value.userId);
  emits('close');
}

/* validation */
let validation = reactive({
  bIdDupCheck: '돋보기버튼을 클릭하여 ID 중복체크를 진행해주세요.',
  bPasswordChk: '돋보기버튼을 클릭하여 비밀번호확인을 진행해주세요.'
});
/* USER 중복검사 */
const idDupCheck = async () => {
  if(props.mode !== MODAL_MODE.REG) return true;

  if(validUtil.isNull(user.value.userId)){
    validation.bIdDupCheck = 'ID를 입력해주세요.';
    return;
  }

  const validUser = await userManageApi.user(user.value.userId);

  if(validUtil.isNull(validUser)){
    alert('사용 가능한 ID 입니다.');
  }else {
    alert('중복된 ID 입니다.');
  }
  validation.bIdDupCheck = validUtil.isNull(validUser) ? true : '중복된 ID 입니다.';
}

/* PASSWORD 확인 검사 */
const passwordChk = () => {
  if(user.value.userPassword !== user.value.userPasswordChk){
    alert('비밀번호가 일치하지 않습니다.')
  } else {
    alert('비밀번호가 일치합니다.');
  }
  validation.bPasswordChk = user.value.userPassword !== user.value.userPasswordChk ? '비밀번호가 일치하지 않습니다.' : true;
}

</script>

<style scoped>
  @import "@/assets/styles/modal.css";
</style>
