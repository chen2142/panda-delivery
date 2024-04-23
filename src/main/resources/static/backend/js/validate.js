
function isValidUsername (str) {
  return ['admin', 'editor'].indexOf(str.trim()) >= 0;
}

function isExternal (path) {
  return /^(https?:|mailto:|tel:)/.test(path);
}

function isCellPhone (val) {
  if (!/^1(3|4|5|6|7|8)\d{9}$/.test(val)) {
    return false
  } else {
    return true
  }
}

//Verify the account
function checkUserName (rule, value, callback){
  if (value == "") {
    callback(new Error("Please enter the account number"))
  } else if (value.length > 20 || value.length <3) {
    callback(new Error("The account length should be 3-20"))
  } else {
    callback()
  }
}

//Check the name
function checkName (rule, value, callback){
  if (value == "") {
    callback(new Error("Please enter a name"))
  } else if (value.length > 12) {
    callback(new Error("The account length should be 1-12"))
  } else {
    callback()
  }
}

function checkPhone (rule, value, callback){
  // let phoneReg = /(^1[3|4|5|6|7|8|9]\d{9}$)|(^09\d{8}$)/;
  if (value == "") {
    callback(new Error("Please enter your mobile phone number"))
  } else if (!isCellPhone(value)) {//Introduced methods for checking the format of mobile phones encapsulated in methods
    callback(new Error("Please enter the correct mobile phone number!"))
  } else {
    callback()
  }
}


function validID (rule,value,callback) {
  // The ID number is 15 or 18 digits, 15 digits are all numbers,
  // the first 17 digits of 18 digits are digits, and the last digit is the check digit,
  // which may be a number or the character X
  let reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/
  if(value == '') {
    callback(new Error('Please enter your ID number'))
  } else if (reg.test(value)) {
    callback()
  } else {
    callback(new Error('The ID number is incorrect'))
  }
}