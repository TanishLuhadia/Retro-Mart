function sendOTP() {
    var phoneNumber = document.getElementById('phoneNumber').value;
    if (phoneNumber.trim() === '') {
      showMessage('Please enter a valid mobile number.', true);
      return;
    }
    // Simulate sending OTP, you can implement your backend logic here.
    var otp = Math.floor(1000 + Math.random() * 9000);

    showMessage('OTP sent to ' + phoneNumber + ': ' + otp);
    document.getElementById('otpContainer').style.display = 'block';
  }
  
  function verifyOTP() {
    var otpEntered = document.getElementById('otp').value;
    var otp = parseInt(otpEntered);
    if (isNaN(otp) || otp.toString().length !== 4) {
      showMessage('Please enter a valid OTP.', true);
      return;
    }
    // Here you can implement your logic to verify OTP.
    showMessage('OTP verified successfully!');
  }
  
  function showMessage(message, isError = false) {
    var messageElement = document.getElementById('message');
    messageElement.innerHTML = message;
    if (isError) {
      messageElement.style.color = 'red';
    } else {
      messageElement.style.color = 'green';
    }
  }
  