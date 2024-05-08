import App from './App.vue'
import router from '@/router/index.js'
import vuetify from "@/plugins/vuetify.js";
import store from "@/store/store.js"
import axios from "axios";
import {createApp} from 'vue'

/* 요청하기전에 수행할 일을 정의한다.
 TODO: 공통헤더 추가하기 (jwt토큰 추가)
*/
axios.interceptors.request.use(
  request => {
    request.headers["Authorization"] = "Bearer " + store.getters.getUser.token;

    let data = request.data??{};
    data.registUserName = store.getters.getUser.userNm;

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

