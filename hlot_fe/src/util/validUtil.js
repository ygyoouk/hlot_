const ValidUtil = {
  name: "ValidUtil",

  addComma(evt) {
    let n = evt.target.value;

    if(ValidUtil.isNull(n)) return false;

    n = n.replace(/[^0-9]/g, '');

    evt.target.value = new Intl.NumberFormat('en-US').format(parseInt(n, 10));
  },

  /**
   * 공백, Null, undefined 체크
   * @param text
   * @returns {boolean|boolean}
   */
  isNull(text) {
    return text === '' ? true
      : text === undefined ? true
        : text === null;
  },

  /**
   * 날짜 포맷팅
   * @param {*} date
   * @returns {yyyy-MM-dd}
   */
  formatDate(date) {
    const year = date.slice(0, 4);
    const month = date.slice(4, 6);
    const day = date.slice(6, 8);

    return `${year}-${month}-${day}`;
  },

  /**
   * yyy-MM-dd => yyyymmdd
   */
  saveDate(date) {
    if (date === undefined || date === null || date === '') return '';
    return date.split("-").join("");
  },

  validDateChk(stDate,endDate){
    const st = new Date(stDate);
    const end = new Date(endDate);

    if(st>end){
      alert("시작일자가 종료일자보다 큽니다");
      return false;
    }
    return true;
  }
  ,

  required(v) {
    if (ValidUtil.isNull(v))  // ValidUtil을 사용하여 isNull 참조
      return '필수항목 항목입니다.';
    else
      return true;
  },

  email(v) {
    if (ValidUtil.isNull(v)) return true;

    if (!/^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/.test(v))
      return '이메일 형식이 아닙니다.';
    else
      return true;
  },

  number(v) {
    if (ValidUtil.isNull(v)) return true;

    let n = v.replaceAll('-', '');
    n = n.replaceAll(',', '');

    if (isNaN(n))
      return '숫자만 입력 가능합니다.';
    else
      return true;
  }
};

export default ValidUtil;
