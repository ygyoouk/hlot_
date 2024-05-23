import axios from "axios";
import validUtil from "@/util/validUtil";

const BASE_URL = import.meta.env.VITE_BASE_URL;
const BE_MANAGEMENT_PORT = import.meta.env.VITE_BE_MANAGEMENT_PORT;
const REQUEST_URL = `${BASE_URL}:${BE_MANAGEMENT_PORT}`;

export default {

  /* USER LOGIN */
  login(user) {
    delete axios.defaults.headers.common.Authorization;
    this.removeUserStorage();

    return axios.post(REQUEST_URL + `/api/login`, user).then(res => {
      const data = res.data.data;
      if(!validUtil.isNull(data)) {
        this.setUserStorage(data);

        location.href=`${BASE_URL}`;
      }
    });
  },

  logout() {
    this.removeUserStorage();
    location.href=`${BASE_URL}/login`;
  },

  removeUserStorage() {
    window.localStorage.removeItem("isLogin");
    window.localStorage.removeItem("userId");
    window.localStorage.removeItem("userName");
    window.localStorage.removeItem("token");
    window.localStorage.removeItem("userAuth");
  },

  setUserStorage(data) {
    window.localStorage.setItem("isLogin", this.createItemWithExpireObj("0"));
    window.localStorage.setItem("userId", this.createItemWithExpireObj(data.userId));
    window.localStorage.setItem("userName", this.createItemWithExpireObj(data.userName));
    window.localStorage.setItem("userAuth", this.createItemWithExpireObj(data.userAuth));
    window.localStorage.setItem("token", this.createItemWithExpireObj(data.accessToken));
  },

  getUserStorage(key) {
    let item = localStorage.getItem(key);
    if(validUtil.isNull(item)) return null;

    item = JSON.parse(item);
    if(Date.now() > item.expire) {
      window.localStorage.removeItem(key);
      return null;
    }
    return item.value;

  },

  createItemWithExpireObj(item) {
    if(validUtil.isNull(item)) return false;

    return JSON.stringify({
      value: item,
      expire: Date.now() + (1000*60*30)
    });
  }


}
