<template>
  <div
    class="login-container"
    @keyup.enter="login"
  >
    <div class="login-form">
      <v-text-field
        v-model="user.userId"
        label="아이디"
      />
      <v-text-field
        v-model="user.userPassword"
        label="패스워드"
        type="password"
      />
      <v-btn
        color="green"
        size="large"
        @click="login"
        block
      >
        로그인
      </v-btn>
    </div>
  </div>
</template>

<script>
import validUtil from "@/util/validUtil";
import user from "@/api/user";

export default {
  name: "Login",
  data() {
    return {
      user:{
        userId:'',
        userPassword:''
      }
    }
  },
  methods: {
    async login(){
      if(validUtil.isNull(this.user.userId)) return false;
      if(validUtil.isNull(this.user.userPassword)) return false;

      const isSuccess = await user.login(this.user);
      if(isSuccess){

      }


    }
  }
}
</script>

<style scoped>
  .login-container {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100%;
  }
  .login-form {
    width: 400px;
    height: 200px;
    border-radius: 10px;
    margin-bottom: 5%;
  }
</style>
