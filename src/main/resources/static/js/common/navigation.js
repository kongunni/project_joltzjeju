/**
 *  common - navigation bar
 */

 
document.addEventListener("DOMContentLoaded", function() {

	logEvent();

    const hamburgerMenu = document.getElementById("hamburger-menu");
    const navWrapper = document.getElementById("nav-wrapper");
    const closeBtn = document.getElementById("close-btn");
    const contactEmail = document.getElementById("contact-email");
    const contactContainer = document.querySelector('.contact-container');
    // 요소가 제대로 선택되었는지 확인
    if (hamburgerMenu && navWrapper && closeBtn) {
        hamburgerMenu.addEventListener("click", function() {
            navWrapper.classList.add("active");
            contactEmail.classList.add("hidden");
            contactContainer.style.display= 'none';
        });

        closeBtn.addEventListener("click", function() {
            navWrapper.classList.remove("active");
            contactContainer.style.display= 'block';
        });
    } else {
        console.error("One or more elements are missing.");
    }
    
    
});




/* 로그인 & 로그아웃 */
const logEvent = () => {
	const loginAction = document.getElementById("loginBtn");
	const logoutAction = document.getElementById("logoutBtn");
	
	
	
	if(loginAction) {
		loginAction.addEventListener("click", () => {
			window.location.href = "/user/login";
		});
	}
	
	if(logoutAction) {
		logoutAction.addEventListener("click", () => {
			fetch("/user/logout", {
				method: "POST"
			}).then(response => {
				if(!response.ok) {
					throw new Error("Logout failed");
				}
				location.reload();
			}).catch(error => {
				alert(error.message);
			});
		});
	}
};