import axios from "axios";

const BASE_URL = import.meta.env.VITE_BASE_URL;
const BE_MANAGEMENT_PORT = import.meta.env.VITE_BE_MANAGEMENT_PORT;
const REQUEST_URL = `${BASE_URL}:${BE_MANAGEMENT_PORT}`;

export default {

  /* TOP_CONTR ID, 명 조회 */
  topContrNms(){
    return axios.get(REQUEST_URL + '/common/topContrNms').then(res => {
      console.log(res.data.data);
      return res.data.data;
    });
  },

  topContrNm(topContrId){
    return axios.get(REQUEST_URL + '/common/topContrNm/'+topContrId).then(res => {
      return res.data.data;
    });
  },

  /* COMP ID, 명 조회 */
  compNms(){
    return axios.get(REQUEST_URL + '/common/compNms').then(res => {
      console.log(res.data.data);
      return res.data.data;

    });
  },

  compNm(compId){
    return axios.get(REQUEST_URL + '/common/compNm/'+compId).then(res => {
      return res.data.data;
    });
  },

  /* 공통코드 조회 조회 */
  cmmCodeComp(codeNm) {
    return axios.get(REQUEST_URL + `/common/code/${codeNm}`).then(res => {
      return res.data.data;
    })
  }
}
