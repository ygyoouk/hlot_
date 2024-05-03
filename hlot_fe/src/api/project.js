import axios from "axios";

const BASE_URL = import.meta.env.VITE_BASE_URL;
const BE_MANAGEMENT_PORT = import.meta.env.VITE_BE_MANAGEMENT_PORT;
const REQUEST_URL = `${BASE_URL}:${BE_MANAGEMENT_PORT}`;

export default {

  // PROJECT 조회
  projects(){
    return axios.get(REQUEST_URL + '/api/project').then(res => {
      return res.data.data;
    });
  },

  // PROJECT 상세조회
  project(projectId){
    return axios.get(REQUEST_URL + '/api/project/'+projectId).then(res => {
      console.log(res.data.data);
      return res.data.data;
    });
  },

  // PROJECT 등록
  newProject(projectData,formData){
    console.log("formData=>" + formData);
    return axios.post(REQUEST_URL + '/api/project',formData,{headers:{'Content-Type' : 'multipart/form-data'}}).then(res => {
      alert("등록되었습니다");

      return res.data.data;
    });
  },

  // PROJECT 삭제
  deleteProject(selectedProjectData){
    return axios.delete(REQUEST_URL + '/api/project',{data : selectedProjectData}).then(res => {
        return res;
    });
  },

}
