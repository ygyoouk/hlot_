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
  }

}
