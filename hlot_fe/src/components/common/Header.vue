<template>
  <v-app-bar flat>
    <v-container class="mx-auto d-flex justify-center header-nav_mt" >

      <v-btn class="header-btn_mt"
        v-for="link in links"
        :key="link.path"
        :text="link.name"
        variant="text"
        @click="movePage(link.path)"
      ></v-btn>

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

    </v-container>
  </v-app-bar>
</template>

<script>
import store from "@/store/store";
import user from "@/api/user";
import validUtil from "@/util/validUtil";

export default {
  name: 'Header',
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
      links: [
        { name: 'HOME',      path: '/' },
        { name: '원계약관리',  path: '/project/projectList' },
        { name: '견적서관리',   path: '/estimate/estimateList' },
        { name: '계약관리',    path: '/contr/contrList' },

        { name: '업체관리',    path: '/company/companyList' },
        { name: '공통코드관리', path: '/' }
      ]
    }
  },
  methods: {
    movePage(path){
      this.$router.push(path);
      if(store.getters.isOpenModal){
        store.commit('toggleModal');
      }
    }
  }
}
</script>
