$(function() {
    $( "#droppableleft" ).droppable({
      drop: function(event, ui) {
        $(this).empty();
        $(this).append(ui.draggable.clone());
      }
    });
    $( "#droppableright" ).droppable({
      drop: function(event, ui) {
        $(this).empty();
        $(this).append(ui.draggable.clone());
      }
    });
    $( "#droppablebottom" ).droppable({
      drop: function(event, ui) {
        $(this).empty();
        $(this).append(ui.draggable.clone());
      }
    });

    $( ".draggableleft" ).draggable({
      connectToSortable: "#sortableleft",
      helper: "clone",
      revert: "invalid",
      delay: 100,
      /*stop: function() {
        $(".leftArm").empty();
      },
      appendTo: ".leftArm"*/
    });





    $(".left .item").click(function(event){
      if($(this).css("width") == "90px"){
        $(this).animate({"width":"250px", "height":"150px"});
      }else{
        $(this).animate({"width":"90px", "height":"90px"});
      }
      
    });

    $(".clickmeleft").click(function(event){
      event.stopPropagation();
      $(".leftArm").empty();
      $(this).parent().children("img").clone().appendTo($(".leftArm"));
     /* $(this).clone().appendTo($(".leftArm"));*/
      
      
    });

    $(".save").click(function(){
      var parts = new Object();
      parts.left = $($("#droppableleft").find("img")[0]).attr("alt");
      parts.right = $($("#droppableright").find("img")[0]).attr("alt")
      parts.bottom = $($("#droppablebottom").find("img")[0]).attr("alt")
      console.log(parts);
    });
  });