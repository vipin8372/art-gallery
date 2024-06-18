document.getElementById("signupButton").addEventListener("click", function() {
    // Disable the button to prevent multiple submissions
    this.classList.add("loading");
    // Show the loading spinner
    document.getElementById("loadingSpinner").classList.add("active");

    // Simulate loading time with setTimeout
    setTimeout(() => {
        // Re-enable the button and hide the loading spinner after some time (simulate loading completion)
        this.classList.remove("loading");
        document.getElementById("loadingSpinner").classList.remove("active");
    }, 2000); // Adjust the duration as needed
});
