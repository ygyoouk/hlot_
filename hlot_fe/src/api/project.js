import axios from "axios";

const BASE_URL = import.meta.env.VITE_BASE_URL;
const BE_MANAGEMENT_PORT = import.meta.env.VITE_BE_MANAGEMENT_PORT;
const REQUEST_URL = `${BASE_URL}:${BE_MANAGEMENT_PORT}`;

export default {

  // TOP_CONTR 조회
  projects(){
    return axios.get(REQUEST_URL + '/api/topContr').then(res => {
      return res.data.data;
    });
  },

  // TOP_CONTR 상세조회
  project(topContrId){
    return axios.get(REQUEST_URL + '/api/topContr/'+topContrId).then(res => {
      console.log(res.data.data);
      return res.data.data;
    });
  },

  // TOP_CONTR 등록
  newProject(projectData){
    console.log(projectData);
    return axios.post(REQUEST_URL + '/api/topContr',projectData).then(res => {
      alert("등록되었습니다");

      return res.data.data;
    });
  },

  // TOP_CONTR 삭제
  deleteProject(selectedProjectData){
    return axios.delete(REQUEST_URL + '/api/topContr',{data : selectedProjectData}).then(res => {
        return res;
    });
  },

}
