<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
  	
    <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
      <script src="/resources/bootstrap/js/html5shiv.js"></script>
    <![endif]-->

    <!-- Fav and touch icons -->
    <link rel="apple-touch-icon-precomposed" sizes="144x144" href="/resources/bootstrap/ico/apple-touch-icon-144-precomposed.png">
	<link rel="apple-touch-icon-precomposed" sizes="114x114" href="/resources/bootstrap/ico/apple-touch-icon-114-precomposed.png">
	<link rel="apple-touch-icon-precomposed" sizes="72x72" href="/resources/bootstrap/ico/apple-touch-icon-72-precomposed.png">
    <link rel="apple-touch-icon-precomposed" href="/resources/bootstrap/ico/apple-touch-icon-57-precomposed.png">
    <link rel="shortcut icon" href="/resources/bootstrap/ico/favicon.png">
</head>

<body>

    <!-- Carousel
    ================================================== -->
    <div id="myCarousel" class="carousel slide">
      <div class="carousel-inner">
        <div class="item active">
          <img src="/resources/bootstrap/img/examples/slide-01.jpg" alt="">
          <div class="container">
            <div class="carousel-caption">
              <h1>GuestHouse 하루!</h1>
              <p class="lead">내일로 성지인 순천!! 환영합니다. 순.하.게</p>
              <p class="lead">'하루'에는 많은 즐거운 일들이 일어납니다. 여행, 낭만, 자유와 행복을 느껴보세요.</p>
              <a class="btn btn-large btn-primary" href="/reserve/index">예약하기</a>
            </div>
          </div>
        </div>
        <div class="item">
          <img src="/resources/bootstrap/img/examples/slide-02.jpg" alt="">
          <div class="container">
            <div class="carousel-caption">
              <h1>Another example headline.</h1>
              <p class="lead">Cras justo odio, dapibus ac facilisis in, egestas eget quam. Donec id elit non mi porta gravida at eget metus. Nullam id dolor id nibh ultricies vehicula ut id elit.</p>
              <a class="btn btn-large btn-primary" href="#">Learn more</a>
            </div>
          </div>
        </div>
        <div class="item">
          <img src="/resources/bootstrap/img/examples/slide-03.jpg" alt="">
          <div class="container">
            <div class="carousel-caption">
              <h1>One more for good measure.</h1>
              <p class="lead">Cras justo odio, dapibus ac facilisis in, egestas eget quam. Donec id elit non mi porta gravida at eget metus. Nullam id dolor id nibh ultricies vehicula ut id elit.</p>
              <a class="btn btn-large btn-primary" href="#">Browse gallery</a>
            </div>
          </div>
        </div>
      </div>
      <a class="left carousel-control" href="#myCarousel" data-slide="prev">&lsaquo;</a>
      <a class="right carousel-control" href="#myCarousel" data-slide="next">&rsaquo;</a>
    </div><!-- /.carousel -->



    <!-- Marketing messaging and featurettes
    ================================================== -->
    <!-- Wrap the rest of the page in another container to center all the content. -->

    <div class="container marketing">

      <!-- Three columns of text below the carousel -->
      <div class="row">
        <div class="span4">
          <img class="img-circle" data-src="holder.js/140x140">
          <h2>예약하기</h2>
          <p>
          	<span class="label label-important">Reservation</span> 메뉴로 이동하신후 원하시는 날짜에 정원이 초과되지 않았을 경우 예약 가능합니다.<br>
          	예약후 입금 하셔야 <span class="label label-success">예약 완료</span>가 됩니다. ^^
          </p>
          <p><a class="btn" href="/reserve/index">예약하기.</a></p>
        </div><!-- /.span4 -->
        <div class="span4">
          <img class="img-circle" data-src="holder.js/140x140">
          <h2>이용금액</h2>
          <p> 
          	기본 : <span class="label label-important">24,000원</span> <br>
          	내일로 : <span class="label label-info">20,000원</span><br>
          	정원박람회 이용객 : <span class="label label-info">20,000원</span><br>
          </p>
          
        </div><!-- /.span4 -->
        <div class="span4">
          <img class="img-circle" data-src="holder.js/140x140">
          <h2>순.하.게 환불규칙</h2>
          <p>
          	<span class="label label-important">7일전 취소 : 숙박비의 100%</span><br>
          	<span class="label label-warning">5일전 취소 : 숙박비의 50%</span><br>
          	<span class="label label-info">3일전 취소 : 숙박비의 30%</span><br>
          	그외의 환불은 어렵습니다. ㅠ_ㅠ
          </p>
        </div><!-- /.span4 -->
      </div><!-- /.row -->


      <!-- START THE FEATURETTES

      <hr class="featurette-divider">

      <div class="featurette">
        <img class="featurette-image pull-right" src="/resources/bootstrap/img/examples/browser-icon-chrome.png">
        <h2 class="featurette-heading">First featurette headling. <span class="muted">It'll blow your mind.</span></h2>
        <p class="lead">Donec ullamcorper nulla non metus auctor fringilla. Vestibulum id ligula porta felis euismod semper. Praesent commodo cursus magna, vel scelerisque nisl consectetur. Fusce dapibus, tellus ac cursus commodo.</p>
      </div>

      <hr class="featurette-divider">

      <div class="featurette">
        <img class="featurette-image pull-left" src="/resources/bootstrap/img/examples/browser-icon-firefox.png">
        <h2 class="featurette-heading">Oh yeah, it's that good. <span class="muted">See for yourself.</span></h2>
        <p class="lead">Donec ullamcorper nulla non metus auctor fringilla. Vestibulum id ligula porta felis euismod semper. Praesent commodo cursus magna, vel scelerisque nisl consectetur. Fusce dapibus, tellus ac cursus commodo.</p>
      </div>

      <hr class="featurette-divider">

      <div class="featurette">
        <img class="featurette-image pull-right" src="/resources/bootstrap/img/examples/browser-icon-safari.png">
        <h2 class="featurette-heading">And lastly, this one. <span class="muted">Checkmate.</span></h2>
        <p class="lead">Donec ullamcorper nulla non metus auctor fringilla. Vestibulum id ligula porta felis euismod semper. Praesent commodo cursus magna, vel scelerisque nisl consectetur. Fusce dapibus, tellus ac cursus commodo.</p>
      </div>

      <hr class="featurette-divider">

       -->
      <!-- /END THE FEATURETTES -->



    </div><!-- /.container -->

	<script type="text/javascript">
        $(function(){
          // carousel demo
          $('#myCarousel').carousel();
        });
    </script>
    
    <!-- <script src="/resources/bootstrap/js/holder/holder.js"></script> -->
  </body>
</html>
