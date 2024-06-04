<template>

  <UserManageModal
    v-if="bUserManageModal"
    @close="closeModal"
    :userId="selectedUserId"
    :mode="userManageModalMode"
  />

  <v-card class="table-container_mt">
    <div class="table-title_mt">
      사용자 관리
    </div>

    <div class="d-flex flex-wrap ga-3 mb-0">
      <v-text-field
        label="아이디"
        density="compact"
        v-model="searchCondition.userId"
      />

      <v-text-field
        label="이름"
        density="compact"
        v-model="searchCondition.userName"
      />
      <v-btn
        color="green"
        @click="getUsers"
      >
        조회
      </v-btn>
    </div>

    <div class="table-btn-list">
      <v-btn color="#5865f2" @click="openModal">등록</v-btn>
    </div>

    <v-data-table
      @click:row="openModal"
      :headers="headers"
      :items="users"
      :items-per-page-options="ITEMS_PER_PAGE_OPTIONS"
      class="elevation-1 table-list_mt"
    ></v-data-table>

  </v-card>

</template>

<script setup>
import {ITEMS_PER_PAGE_OPTIONS, MODAL_MODE} from "@/util/config";
import {onBeforeMount, reactive, ref} from "vue";
import userManageApi from '@/api/userManage'
import UserManageModal from "@/components/modal/UserManageModal.vue";
import validUtil from "@/util/validUtil";

onBeforeMount(() => {
  getUsers();
});

/* 목록조회 관련 */
const headers = [
  { title: 'ID', key:'userId'},
  { title: '이름', key:'userName'},
  { title: '권한', key:'userAuthNm'},
];

// 검색조건
const searchCondition = reactive({userId: '', userName: ''});

// user 목록 조회
let users = ref([]);
const getUsers = async () => {
  users.value = await userManageApi.users(searchCondition);
}

// 모달창 제어
let bUserManageModal = ref(false);
let selectedUserId = ref('');
let userManageModalMode = ref('');
const openModal = (event, row) => {
  userManageModalMode.value = validUtil.isNull(row) ? MODAL_MODE.REG : MODAL_MODE.MOD;
  selectedUserId.value = validUtil.isNull(row) ? '' : row.item.userId;
  bUserManageModal.value = true;
}

const closeModal = () => {
  bUserManageModal.value = false;
  getUsers();
}
</script>
