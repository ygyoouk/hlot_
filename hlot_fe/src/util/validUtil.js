export default {
  name: "ValidUtil",

  /**
   * 공백, Null, undefined 체크
   * @param text
   * @returns {boolean|boolean}
   */
  isNull(text){
    return text === '' ? true
          : text === undefined ? true
          : text === null;
  },

  /**
   * 날짜 포맷팅
   * @param {*} date 
   * @returns {yyyy-MM-dd}
   */
   formatDate(date){
    const year = date.slice(0,4);
    const month = date.slice(4,6);
    const day = date.slice(6,8);

    return `${year}-${month}-${day}`;
    },

    /**
     * yyy-MM-dd => yyyymmdd
     */
    saveDate(date){

      return date.split("-").join("");

    }    

}
