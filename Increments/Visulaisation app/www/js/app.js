// Ionic Starter App

// angular.module is a global place for creating, registering and retrieving Angular modules
// 'starter' is the name of this angular module example (also set in a <body> attribute in index.html)
// the 2nd parameter is an array of 'requires'
// 'starter.services' is found in services.js
// 'starter.controllers' is found in controllers.js
var imageApp = angular.module('starter', ['ionic','ngCordova','starter.controllers', 'starter.services'])

imageApp.run(function($ionicPlatform) {
  $ionicPlatform.ready(function() {
    // Hide the accessory bar by default (remove this to show the accessory bar above the keyboard
    // for form inputs)
    if (window.cordova && window.cordova.plugins && window.cordova.plugins.Keyboard) {
      cordova.plugins.Keyboard.hideKeyboardAccessoryBar(true);
      cordova.plugins.Keyboard.disableScroll(true);

    }
    if (window.StatusBar) {
      // org.apache.cordova.statusbar required
      StatusBar.styleDefault();
    }
  });
})

imageApp.directive('onLastRepeat', function() {
return function(scope, element, attrs) {
if (scope.$last) setTimeout(function(){
scope.$emit('onRepeatLast', element, attrs);
}, 1);
};
})

imageApp.config(function($stateProvider, $urlRouterProvider) {

  
  $stateProvider

   
    .state('tab', {
    url: '/tab',
    abstract: true,
    templateUrl: 'templates/tabs.html'
  })

  // Each tab has its own nav history stack:

  .state('tab.dash', {
    url: '/dash',
    views: {
      'tab-dash': {
        templateUrl: 'templates/tab-dash.html',
        controller: 'AnalysisController'
      }
    }
  })

  .state('tab.chats', {
      url: '/chats',
      views: {
        'tab-chats': {
          templateUrl: 'templates/tab-chats.html',
          controller: 'AnalysisTwoController'
        }
      }
    })
    .state('tab.chat-detail', {
      url: '/chats/:chatId',
      views: {
        'tab-chats': {
          templateUrl: 'templates/chat-detail.html',
          controller: 'ChatDetailCtrl'
        }
      }
    })
    .state('tab.recommend', {
      url: '/recommend',
      params : {
          obj : null
      },
      views: {
        'tab-recommend': {
          templateUrl: 'templates/tab-recommend.html',
          controller: 'RecommendationController'
        }
      }
    })

  .state('tab.account', {
    url: '/account',
      
    views: {
      'tab-account': {
        templateUrl: 'templates/tab-account.html',
        controller: 'RecommendController'
      }
    }
  });

  // if none of the above states are matched, use this as the fallback
$urlRouterProvider.otherwise('/tab/dash');

});

google.charts.load('current', {packages: ['corechart', 'line']});
    
imageApp.controller("AnalysisController", function($scope, $http,$state) {
    
    
    
        var options = {
    date: new Date(),
    mode: 'datetime', // or 'time'
    minDate: new Date() - 10000,
    allowOldDates: true,
    allowFutureDates: false,
    doneButtonLabel: 'DONE',
    doneButtonColor: '#F2F3F4',
    cancelButtonLabel: 'CANCEL',
    cancelButtonColor: '#000000'
  };
var time_r,date_r;
   date_r = new Date();
    console.log(date_r);
    
    month_r = date_r.getMonth()+1;
    month_r = month_r < 10 ? '0' + month_r : '' + month_r;
    
    year_r = date_r.getFullYear();
    date1_r = date_r.getDate();
    date1_r = date1_r < 10 ? '0' + date1_r : '' + date1_r;
    
      time_r = year_r + '-'+month_r+'-'+date1_r;  
           console.log(time_r);
    
  $scope.datepick = function () {
      
     
      $cordovaDatePicker.show(options).then(function(date){
        alert(date);
            
          date_r = new Date();
    console.log(date_r);
    
    month_r = date_r.getMonth()+1;
    month_r = month_r < 10 ? '0' + month_r : '' + month_r;
    
    year_r = date_r.getFullYear();
    date1_r = date_r.getDate();
    date1_r = date1_r < 10 ? '0' + date1_r : '' + date1_r;
    
      time_r = year_r + '-'+month_r+'-'+date1_r;  
           
          
          
        
    },function(error) {
       alert("an error occured");
      });

  }
  
  $http.get('http://heartfitapp.netne.net/get_out_apriori.php?out_date=2016-05-05').success(function(data) {
    jso_var = data.split("<");
    console.log(jso_var[0]);
    var obj = JSON.parse(jso_var[0]);
        console.log(obj[0].out_hr);
        var arr1 = [];
        arr1.push(['ID', 'Hour', 'Heart Rate','Type', 'Occurence']);
        for(i=0;i<obj.length;i++){
            var temp_arr = [];
            temp_arr.push(obj[i].out_num);
            temp_arr.push( parseInt(obj[i].out_hour));
            temp_arr.push( parseInt(obj[i].out_hr));
            temp_arr.push('HeartBeat');
            temp_arr.push(parseInt(obj[i].out_num));
            console.log(obj[i].out_hour,obj[i].out_hr);
            arr1.push(temp_arr);
        }
      
    
    google.charts.setOnLoadCallback(drawSeriesChart);

    function drawSeriesChart() {

   /*   var data = google.visualization.arrayToDataTable([
        ['ID', 'Life Expectancy', 'Fertility Rate', 'Region',     'Population'],
        ['',    80.66,              1.67,      'North America',  33739900],
        ['',    79.84,              1.36,      'Europe',         81902307],
        ['',    78.6,               1.84,      'Europe',         5523095],
        ['',    72.73,              2.78,      'Middle East',    79716203],
        ['GBR',    80.05,              2,         'Europe',         61801570],
        ['IRN',    72.49,              1.7,       'Middle East',    73137148],
        ['IRQ',    68.09,              4.77,      'Middle East',    31090763],
        ['ISR',    81.55,              2.96,      'Middle East',    7485600],
        ['RUS',    68.6,               1.54,      'Europe',         141850000],
        ['USA',    78.09,              2.05,      'North America',  307007000]
      ]);
*/
         var data = google.visualization.arrayToDataTable(arr1);
      var options = {
        title: 'Heart rate pattern throughtout the day ' ,
        hAxis: {title: 'Hour'},
        vAxis: {title: 'Heart Rate'},
        bubble: {textStyle: {fontSize: 11}},
          
          hAxis: {
    viewWindow: {
        min: 0,
        max: 24
    },
    ticks: [0,4,8,12,16,20,23] // display labels every 4
},
                vAxis: {
    viewWindow: {
        min: 40,
        max: 160
    },
    ticks: [40,60,80,100,120,140,160] // display labels every 4
}
      };

      var chart = new google.visualization.BubbleChart(document.getElementById('barchart'));
      chart.draw(data, options);
    }
    })
});

imageApp.controller("AnalysisTwoController", function($scope, $http,$state,$cordovaDatePicker) {
    
    var options = {
    date: new Date(),
    mode: 'date', // or 'time'
    minDate: new Date() - 10000,
    allowOldDates: true,
    allowFutureDates: false,
    doneButtonLabel: 'DONE',
    doneButtonColor: '#F2F3F4',
    cancelButtonLabel: 'CANCEL',
    cancelButtonColor: '#000000'
  };
var time_r,date_r;
   date_r = new Date();
    console.log(date_r);
    
    month_r = date_r.getMonth()+1;
    month_r = month_r < 10 ? '0' + month_r : '' + month_r;
    
    year_r = date_r.getFullYear();
    date1_r = date_r.getDate();
    date1_r = date1_r < 10 ? '0' + date1_r : '' + date1_r;
    
      time_r = year_r + '-'+month_r+'-'+date1_r;  
           console.log(time_r);
    
  $scope.datepick = function () {
      
     
      $cordovaDatePicker.show(options).then(function(date){
        alert(date);
       /* newdate = date;
        month = date.substring(4,7);
    console.log( "JanFebMarAprMayJunJulAugSepOctNovDec".indexOf(month) / 3 + 1 );
        alert(month);
        date1= date.substring(8,10);
        year1= date.substring(11,15);
        var month_r = "JanFebMarAprMayJunJulAugSepOctNovDec".indexOf(month)/3 + 1; 
      month_r = month_r < 10 ? '0' + month_r : '' + month_r;
      time_r = year1 + '-'+month_r+'-'+date1;  
           console.log(time_r);*/
          
          date_r = new Date();
    console.log(date_r);
    
    month_r = date_r.getMonth()+1;
    month_r = month_r < 10 ? '0' + month_r : '' + month_r;
    
    year_r = date_r.getFullYear();
    date1_r = date_r.getDate();
    date1_r = date1_r < 10 ? '0' + date1_r : '' + date1_r;
    
      time_r = year_r + '-'+month_r+'-'+date1_r;  
           alert(time_r);
          
          
        
    },function(error) {
       alert("an error occured");
      });

  }
        
    console.log(time_r);
    var jso_var;
    $http.get('http://heartfitapp.netne.net/get_out_accelerometer.php?out_date=2016-05-05').success(function(data) {
    jso_var = data.split("<");
    console.log(jso_var[0]);
    var obj = JSON.parse(jso_var[0]);
        console.log(obj[0].heart_rate);
        var arr1 = [];
        
        for(i=0;i<obj.length;i++){
            var temp_arr = [];
            temp_arr.push(parseInt(obj[i].out_hour));
            temp_arr.push( parseInt(obj[i].out_count));
            console.log(obj[i].out_hour,obj[i].out_count);
            arr1.push(temp_arr);
        }
        console.log(arr1);
  
     
        google.charts.setOnLoadCallback(drawBasic);

function drawBasic() {

      var data = new google.visualization.DataTable();
      data.addColumn('number', 'X');
      data.addColumn('number', 'Movement');

      data.addRows(arr1);

      var options = {
        hAxis: {
          title: 'Hour',
           
        },
        vAxis: {
          title: 'Sleeping Movements'
        }
      };

      var chart = new google.visualization.LineChart(document.getElementById('chart_div'));

      chart.draw(data, options);
    }
        
    })
    
});

imageApp.controller("RecommendController", function($scope, $http,$state) {
    
    var main_str;
    $scope.callClick = function() {
        
//        alert("i was clicked");
        $http.get('write over here').success(function(data) {
            
        })
    
};
  $scope.searchBtn = function() {
      
      main_str = document.getElementById('search_bar').value;
      console.log(main_str);
  
    
    console.log($scope.searchBtn);
    $http.get('http://heartfitapp.netne.net/get_food_cats.php?out_pattern='+main_str).success(function(data) {
    jso_var = data.split("<");
    
    var obj = JSON.parse(jso_var[0]);
        console.log(obj[0].shrt_desc);
        
        var temp_json = {};
       
        var local_str;
       localStorage.setItem('rc_json_length',obj.length);      
        for(i=0;i<obj.length;i++){
            local_str = 'rc_temp_json_'+i;
            console.log(local_str); 
            temp_json['shrt_desc'] = obj[i].shrt_desc;
            temp_json['pro_factor'] = obj[i].pro_factor;
            temp_json['cho_factor'] = obj[i].cho_factor;
            temp_json['fat_factor'] = obj[i].fat_factor;
            temp_json['ndb_no'] = obj[i].ndb_no;
//            $scope.recordsDis.push(temp_json);
              localStorage.setItem(local_str, JSON.stringify(temp_json));
            
            console.log(temp_json);
        }
          
    
    })
     $scope.recordsDis = [];
   var sample_str;
    var json_len = parseInt(localStorage.getItem('rc_json_length'));
    console.log(json_len);
    for(i=0;i<json_len;i++){
        sample_str = 'rc_temp_json_'+i;
       $scope.recordsDis.push(JSON.parse(localStorage.getItem(sample_str)));
        
    }
    console.log($scope.recordsDis);
};    
    
$scope.newValue = function(value) {
     console.log(value);
    $state.go('tab.recommend',{obj:value});
}
    
});

imageApp.controller("RecommendationController", function($scope, $http,$state) {
    
    console.log($state.params.obj);
    
    $scope.$on('onRepeatLast', function(scope, element, attrs){
console.log(element);
        
});
    
    
   $http.get('http://heartfitapp.netne.net/get_recs.php?out_nbid='+$state.params.obj).success(function(data) {
    
       console.log(data);
       jso_var = data.split("<");
    
    var obj = JSON.parse(jso_var[0]);
        console.log(obj[0].shrt_desc);
        
        var temp_json = {};
       
        var local_str;
       localStorage.setItem('json_length',obj.length);
        for(i=0;i<obj.length;i++){
            local_str = 'temp_json_'+i;
            console.log(local_str);
            $scope.idnum = obj[i].ndb_no;
            temp_json['shrt_desc'] = obj[i].shrt_desc;
            temp_json['pro_factor'] = obj[i].pro_factor;
            temp_json['cho_factor'] = obj[i].cho_factor;
            temp_json['fat_factor'] = obj[i].fat_factor;
            temp_json['ndb_no'] = obj[i].ndb_no;
             localStorage.setItem(local_str, JSON.stringify(temp_json));
            
            console.log(temp_json);
            console.log($scope.recordsDis);
        }
         console.log(JSON.parse(localStorage.getItem('temp_json_0')).shrt_desc);
       
       
          
    })
   
    $scope.recordsDis = [];
   var sample_str;
    var json_len = parseInt(localStorage.getItem('json_length'));
    console.log(json_len);
    for(i=0;i<json_len;i++){
        sample_str = 'temp_json_'+i;
       $scope.recordsDis.push(JSON.parse(localStorage.getItem(sample_str)));
        
    }
    console.log($scope.recordsDis);
    
//   
    
});