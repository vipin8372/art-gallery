document.getElementById('rzp-button1').onclick = function (e) {

         var totalAmount = *[[${total}]]*; // Total amount passed from backend

         var options = {
             "key": "rzp_test_ELomGfybc6cv1M", // Replace with your actual Razorpay API key
             "amount": totalAmount * 100, // Convert to paise
             "currency": "INR",
             "name": "ART",
             "description": "Test Transaction",
             "handler": function (response) {
                 document.getElementById('razorpay_payment_id').value = response.razorpay_payment_id;
                 document.forms[0].submit();
             },
             "prefill": {
                 "name": "Vipin",
                 "email": "vipinppnr@gmail.com",
                 "contact": "9037773622"
             },
             "theme": {
                 "color": "#3399cc"
             }
         };
         var rzp1 = new Razorpay(options);
         rzp1.open();
         e.preventDefault();
     }