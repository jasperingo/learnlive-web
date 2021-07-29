'strict';


var copyInput = document.getElementById("copy-input");
var copyButton = document.getElementById("copy-button");
if (copyButton !== null) {
    copyButton.addEventListener("click", copyFunction);
}


function copyFunction() {
  
    copyInput.type = "text";
    copyInput.select();
    copyInput.setSelectionRange(0, 99999); 
    document.execCommand("copy");
    copyInput.type = "hidden";
    
    var toast = new bootstrap.Toast(document.getElementById('copyToast'));
    toast.show();
} 




