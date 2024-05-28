import axios from "axios";

const BASE_URL = import.meta.env.VITE_BASE_URL;
const BE_MANAGEMENT_PORT = import.meta.env.VITE_BE_MANAGEMENT_PORT;
const REQUEST_URL = `${BASE_URL}:${BE_MANAGEMENT_PORT}`;

export default {
  /* PROD 목록조회 */
  prods(id, params){
    console.log("params ===>" + JSON.stringify(params));

    let url;

    if(id === undefined || id === null || id === ''){
      url =  '/api/prods';

    }else{
      url=`/api/prods/${id}`;
    }

    console.log(url)

    return axios.get(REQUEST_URL + url,{params}).then(res => {
      return res.data.data;
    });
  },
}
