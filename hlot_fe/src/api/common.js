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
    console.log(codeNm)
    let url;

    if(codeNm === undefined || codeNm === null || codeNm === ''){
      url =  '/common/code';

    }else{
      url=`/common/code/${codeNm}`;
    }

    console.log("URL : " + url);

    return axios.get(REQUEST_URL + url).then(res => {
      console.log(res);
      return res.data.data;
    })
  },

  /* 코드그룹 조회 조회 */
  codeGroup(){
    return axios.get(REQUEST_URL + '/common/codeGroup').then(res =>{
      return res.data.data;
    });
  },

  /* 공통코드 추가*/
  newCmmCode(code){
    return axios.post(REQUEST_URL + '/common/newCmmCode',code).then(res=>{
      return res.data.data;
    });
  },

  /*공통코드 삭제*/
  deleteCmmCode(code){
    console.log(code);
    return axios.delete(REQUEST_URL + '/common/delCmmCode',{data : {"code" : code}}).then(res=>{
      return res;
    });
  },

  selectLatestTopContrInfo(userName){
    return axios.get(REQUEST_URL + '/common/selectLatestTopContrInfo/'+ userName).then(res=>{
      console.log(res.data.data);
      return res.data.data;
    });
  },

  selectLatestEstimateInfo(userName){
    return axios.get(REQUEST_URL + '/common/selectLatestEstimateInfo/'+ userName).then(res=>{
      console.log(res.data.data);
      return res.data.data;
    });
  }


}
