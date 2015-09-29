Parse.Cloud.afterSave("FixtureItem", function(request) {
  var pushQuery = new Parse.Query(Parse.Installation);
  Parse.Push.send({
      where: pushQuery,
      data: {
          alert: "Someone just updated the fixture!"
      }
  }, {
      success: function() {
          // Push successful
      },
      error: function(error) {
          throw "Got an error " + error.code + " : " + error.message;
      }
  });
});
