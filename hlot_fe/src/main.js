import App from './App.vue'
import router from '@/router/index.js'
import vuetify from "@/plugins/vuetify.js";
import store from "@/store/store.js"
import axios from "axios";
import {createApp} from 'vue'
import validUtil from "@/util/validUtil";
import user from "@/api/user";

/* 요청하기전에 수행할 일을 정의한다.
*/
axios.interceptors.request.use(
  request => {
    const t = user.getUserStorage('token');
    const url = request.url;

    if(url.substring(url.lastIndexOf('/')) !== '/login'){
      if(!validUtil.isNull(t)){
        request.headers.Authorization = "Bearer " + t;
        // axios.defaults.headers.common['Authorization'] =  "Bearer " + t;
      }
      else {
        alert("다시 로그인 해주세요.");
        user.logout();
      }
    }


    let data = request.data??{};
    data.registUserName = user.getUserStorage('userNm');

    request.data = data;
    return request;
  }
)

/* 요청후 response를 받아 작업을 수행한다. */
axios.interceptors.response.use(
  response => { /* 성공시 */
    const data = response.data;
    if(data.alert) alert(data.message);

    return response;
  },
  error => { /* 에러 발생시 */
    console.log(error);
    alert("처리에 실패하였습니다.")
    return Promise.reject(error);
  }
)


createApp(App)
  .use(router)
  .use(vuetify)
  .use(store)
  .mount('#app');

