var elementToAdd;

$(function() {
  
  $(".addcondition").click(function(){
    elementToAdd = $(this).parent();
    document.getElementById('airules').style.display = "none";
    document.getElementById('aiconditions').style.display = "block";
  });

  $(".addnewcondition").click(function(){
    elementToAdd = $(this).parent();
    document.getElementById('airules').style.display = "none";
    document.getElementById('aiconditions').style.display = "block";
  });

  $(".addrule").click(function() {
    elementToAdd = $(this).parent();
    document.getElementById('aiconditions').style.display = "none";
    document.getElementById('airules').style.display = "block";
  });

  $(".addme").click(function(event) {
    event.stopPropagation();
    $(elementToAdd).append($(this).parent().children(".button").clone());
    
    //alert("clicked");
   /* $(this).parent().children().appendto($(elementToAdd));*/
  });


  $(".visual #aiconditions td").click(function(event){
    
    $(".explanation").empty();
    $(this).clone().appendTo($(".explanation"));
  });

  $(".visual #airules td").click(function(){
    
    $(".explanation").empty();
    $(this).clone().appendTo($(".explanation"));
  });



  $(".save").click(function(){
    var ruleset = new Object();
    ruleset.rules = new Array();
    


    /*function AiRule(conditions, rules){
      this.conditions = conditions;
      this.actions = actions;
    }*/
    
    airules = new Array();
    /*fetch all airules */
    airules = $(".airule");
    
    /*fetch all conditions for every airule*/
    for(var i=0;i<airules.length; i++){
      airules[i].aiconditions = new Array();
      airules[i].aiconditions = $(airules[i]).find(".condition");
      //console.log(airules[i].aiconditions);

    }

    /*fetch all actions for every airule*/
    for(var i=0; i<airules.length; i++){
      airules[i].actions = new Array();
      airules[i].actions = $(airules[i]).find(".ruleTop");
      //console.log(airules[i].rules);
    }

    
    
   // console.log(airules);
    //console.log(airules[0].aiconditions.length);
   // console.log(airules[1].aiconditions);

  /*Clean condition (leave only the condition text)*/
    for(var i=0; i<airules.length; i++){
      var newrule = new Object();
      newrule.conditions = new Array();
      for(var j=0; j< airules[i].aiconditions.length; j++){
        var span = $(airules[i].aiconditions[j]).find("span")[0];
        if(span){
          //console.log(span);
          newrule.conditions.push(span.innerText);
        }
      }
      for(var h=0; h< airules[i].actions.length; h++){
        //console.log(airules[i].actions[j]);
        var span = $(airules[i].actions[j]).find("span")[0];
        if(span){
          newrule.actions.push(span.innerText);
        }
      }
     
      ruleset.rules.push(newrule);
    }

    console.log(JSON.stringify(ruleset));

   

  });

  });
