/**
 * 회원가입 유효성 검사
 */

  $(document).ready(function() {
	 // 이메일 유효성 검사
	 function validateEmail() {
		 var email = $('#email').val();
		 var emailDomain = $('#emailDomain').val();
		 var fullEmail = email + '@' + emailDomain;

		 if (emailDomain === 'custom') {
			 fullEmail = email + '@' + $('#customEmail').val();
		 }

		 $.ajax({
			 type: 'GET',
			 url: '/user/isEmailDuplicated?email=' + fullEmail,
			 success: function(response) {
				 var status = $('#emailStatus');
				 if (response === 'duplicate') {
					 status.text('이미 등록된 이메일입니다.');
					 status.removeClass('success').addClass('error');
				 } else if (response === 'available') {
					 status.text('사용 가능한 이메일입니다.');
					 status.removeClass('error').addClass('success');
				 } else {
					 status.text('중복 확인 중 오류가 발생했습니다.');
					 status.removeClass('success').addClass('error');
				 }
			 },
			 error: function() {
				 $('#emailStatus').text('중복 확인 중 오류가 발생했습니다.');
				 $('#emailStatus').removeClass('success').addClass('error');
			 }
		 });
	 }

	 // 이메일 도메인 선택 시
	 $('#emailDomain').change(function() {
		 if ($(this).val() === 'custom') {
			 $('#customEmailDiv').show();
		 } else {
			 $('#customEmailDiv').hide();
		 }
	 });

	 // 이메일 입력란 유효성 검사
	 $('#email, #emailDomain, #customEmail').on('blur', function() {
		 validateEmail();
	 });

	 // 사용자 이름 유효성 검사 (한국어)
	 $('#username').on('input', function() {
		 var username = $(this).val();
		 var regex = /^[가-힣]+$/;
		 if (!regex.test(username)) {
			 $('#usernameStatus').text('한국어로만 입력 가능합니다.').addClass('error');
		 } else {
			 $('#usernameStatus').text('').removeClass('error');
		 }
	 });

	 // 비밀번호 유효성 검사
	 $('#pwd').on('input', function() {
		 var pwd = $(this).val();
		 var regex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]).{8,}$/;
		 if (!regex.test(pwd)) {
			 $('#passwordStatus').text('비밀번호는 대소문자, 특수문자, 숫자를 포함해야 합니다.').addClass('error');
		 } else {
			 $('#passwordStatus').text('').removeClass('error');
		 }
	 });

	 // 비밀번호 확인 유효성 검사
	 $('#pwd, #passwordCheck').on('input', function() {
		 var pwd = $('#pwd').val();
		 var passwordCheck = $('#passwordCheck').val();
		 if (pwd !== passwordCheck) {
			 $('#passwordCheckStatus').text('비밀번호가 일치하지 않습니다.').addClass('error');
			 $('#submitButton').attr('disabled', true);
		 } else {
			 $('#passwordCheckStatus').text('').removeClass('error');
			 $('#submitButton').attr('disabled', false);
		 }
	 });

	 // 생일 유효성 검사
	 $('#birthday').on('input', function() {
		 var birthday = $(this).val();
		 var regex = /^\d{8}$/;
		 if (!regex.test(birthday)) {
			 $('#birthdayStatus').text('생일은 8자리 숫자로 입력해야 합니다. 예: 19900101').addClass('error');
		 } else {
			 $('#birthdayStatus').text('').removeClass('error');
		 }
	 });

	 // 전화번호 유효성 검사
	 $('#phone').on('input', function() {
		 var phone = $(this).val();
		 var regex = /^\d{11}$/;
		 if (!regex.test(phone)) {
			 $('#phoneStatus').text('전화번호는 11자리 숫자로 입력해야 합니다.').addClass('error');
		 } else {
			 $('#phoneStatus').text('').removeClass('error');
		 }
	 });
 });
