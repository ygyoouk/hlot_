<template>
  <v-app-bar flat>
    <v-container class="mx-auto d-flex justify-center header-nav_mt" >

      <v-btn class="header-btn_mt"
        v-for="link in links"
        :key="link.path"
        :text="link.name"
        variant="text"
        @click="this.$router.push(link.path)"
      ></v-btn>

      <v-spacer></v-spacer>

      <v-responsive max-width="130">
        <v-btn class="header-btn_mt"
          variant="outlined"
          color="white"
          @click="search"
        >로그인</v-btn>
      </v-responsive>

    </v-container>
  </v-app-bar>
</template>

<script>
export default {
  name: 'Header',
  data(){
    return{
      links: [
        { name: 'HOME',      path: '/' },
        { name: '업체관리',    path: '/company/companyList' },
        { name: '견적서관리',   path: '/estimate/estimateList' },
        { name: '프로젝트관리',  path: '/project/projectList' },
      ]

    }
  },
}
</script>
