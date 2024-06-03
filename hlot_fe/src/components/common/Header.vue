<template>
  <v-app-bar flat>
    <v-container class="mx-auto d-flex justify-center header-nav_mt" >
      <v-row>
        <v-btn
          class="header-btn_mt"
          @click="movePage('/')"
        >
          HOME
        </v-btn>

        <v-btn
          class="header-btn_mt"
          @click="movePage('/project/projectList')"
        >
          원계약관리
        </v-btn>

        <v-menu>
          <template v-slot:activator="{ props }">
            <v-btn
              v-bind="props"
              class="header-btn_mt"
            >
              견적서관리
            </v-btn>
          </template>
          <v-list>
            <v-list-item>
              <v-list-item-title
                class="sub-menu-item_mt"
                @click="movePage('/estimate/estimateList')"
              >견적서 등록</v-list-item-title>

              <v-list-item-title
                class="sub-menu-item_mt"
                @click="movePage('/estimate/estimateCompare')"
              >견적서 비교</v-list-item-title>

              <v-list-item-title
                class="sub-menu-item_mt"
                @click="movePage('/estimate/prodList')"
              >품목조회</v-list-item-title>
            </v-list-item>

          </v-list>
        </v-menu>

        <v-btn
          class="header-btn_mt"
          @click="movePage('/contr/contrList')"
        >
          계약조회
        </v-btn>

        <v-menu>
          <template v-if="user.getUserStorage('userAuth') === '00'" v-slot:activator="{ props }">
            <v-btn
              v-bind="props"
              class="header-btn_mt"
            >
              관리자
            </v-btn>
          </template>
          <v-list>
            <v-list-item>
              <v-list-item-title
                class="sub-menu-item_mt"
                @click="movePage('/company/companyList')"
              >업체관리</v-list-item-title>

              <v-list-item-title
                class="sub-menu-item_mt"
                @click="movePage('/cmmCode/cmmCodeList')"
              >공통코드관리</v-list-item-title>
            </v-list-item>

          </v-list>
        </v-menu>


        <v-spacer></v-spacer>

        <v-responsive max-width="130">
          <v-btn class="header-btn_mt"
            variant="outlined"
            color="white"
            v-if="isLogin"
            @click="this.$router.push('/login')"
          >로그인</v-btn>
        </v-responsive>
        <v-responsive max-width="130">
          <v-btn class="header-btn_mt"
                 variant="outlined"
                 color="white"
                 v-if="!isLogin"
                 @click="user.logout()"
          >로그아웃</v-btn>
        </v-responsive>
      </v-row>
    </v-container>
  </v-app-bar>
</template>

<script>
import store from "@/store/store";
import user from "@/api/user";
import validUtil from "@/util/validUtil";

export default {
  name: 'Header',
  beforeMount() {
    this.links = [
      { name: 'HOME',      path: '/' },
      { name: '원계약관리',  path: '/project/projectList' },
      { name: '견적서관리',   path: '/estimate/estimateList' },
      { name: '계약조회',    path: '/contr/contrList' },

      { name: '업체관리',    path: '/company/companyList' },
      { name: '공통코드관리', path: '/cmmCode/cmmCodeList' }
    ];

    if(user.getUserStorage('userAuth') === '00'){
      this.links.push(
        {name: '관리자', path: null}
      );
    }
  },
  computed: {
    user() {
      return user
    },
    store() {
      return store
    }
  },
  data(){
    return{
      isLogin: validUtil.isNull(user.getUserStorage("isLogin")),
      links: []
    }
  },
  methods: {
    movePage(path){
      if(validUtil.isNull(path)) return false;

      this.$router.push(path);
      if(store.getters.isOpenModal){
        store.commit('toggleModal');
      }
    },

  }
}
</script>
