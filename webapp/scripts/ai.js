var elementToAdd;

$(function() {
  
  $(".addcondition").click(function(){
    elementToAdd = this;
    document.getElementById('airules').style.display = "none";
    document.getElementById('aiconditions').style.display = "block";
  });

  $(".addnewcondition").click(function(){
    elementToAdd = this;
    document.getElementById('airules').style.display = "none";
    document.getElementById('aiconditions').style.display = "block";
  });

  $(".addrule").click(function() {
    elementToAdd = this;
    document.getElementById('aiconditions').style.display = "none";
    document.getElementById('airules').style.display = "block";
  });

  $(".addme").click(function(event) {
    event.stopPropagation();
    alert("clicked");
   /* $(this).parent().children().appendto($(elementToAdd));*/
  });


  $(".visual #aiconditions td").click(function(event){
    console.log("clickedTD");
    $(".explanation").empty();
    $(this).clone().appendTo($(".explanation"));
  });

  $(".visual #airules td").click(function(event){
    console.log("clickedTD");
    $(".explanation").empty();
    $(this).clone().appendTo($(".explanation"));
  });

  });
