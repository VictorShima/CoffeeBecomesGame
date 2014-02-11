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
      newrule.actions = new Array();
      var conditionObj = new Object();
      for(var j=0; j< airules[i].aiconditions.length; j++){
        var span = $(airules[i].aiconditions[j]).find("span")[0];
        if(span){
          //console.log(span);
          conditionObj.name = span.innerText;
        }

        var param = $(airules[i].aiconditions[j]).find("select")[0];
        if(param){
          //console.log(span);
          conditionObj.param = $(param).val();
        }
        if(conditionObj){
          newrule.conditions.push(conditionObj);
        }
      }
      //Clean actions (leave only the action text)
      for(var h=0; h< airules[i].actions.length; h++){
        var actionObj = new Object();
        var span = $(airules[i].actions[h]).find("span")[0];
        if(span){
          actionObj.name = span.innerText;
        }
        var param = $(airules[i].actions[h]).find("select")[0];
        if(param){
          actionObj.param = $(param).val();
        }
         if(actionObj){
          newrule.actions.push(actionObj);
        }
      }
     
      ruleset.rules.push(newrule);
    }

    console.log(JSON.stringify(ruleset));

   

  });

  });
