FROM tomcat:8

ENV ACTIVITI_VERSION 6.0.0
ENV CATALINA_HOME /usr/local/tomcat
ENV PATH $CATALINA_HOME/bin:$PATH
ENV APP_PATH $CATALINA_HOME/webapps/activiti-app
ENV ADMIN_PATH $CATALINA_HOME/webapps/activiti-admin
ENV REST_PATH $CATALINA_HOME/webapps/activiti-rest

COPY activiti-$ACTIVITI_VERSION.zip .
RUN unzip activiti-$ACTIVITI_VERSION.zip \
  && mkdir -p $APP_PATH \
  && mkdir -p $ADMIN_PATH \
  && mkdir -p $REST_PATH \
  && mv activiti-$ACTIVITI_VERSION/wars/activiti-app.war $APP_PATH \
  && mv activiti-$ACTIVITI_VERSION/wars/activiti-admin.war $ADMIN_PATH \
  && mv activiti-$ACTIVITI_VERSION/wars/activiti-rest.war $REST_PATH \
  && rm -rf activiti-$ACTIVITI_VERSION activiti-$ACTIVITI_VERSION.zip \
  && cd $APP_PATH \
  && unzip activiti-app.war \
  && rm -rf activiti-app.war \
  && cd $ADMIN_PATH \
  && unzip activiti-admin.war \
  && rm -rf activiti-admin.war \
  && cd $REST_PATH \
  && unzip activiti-rest.war \
  && rm -rf activiti-rest.war
#VOLUME $APP_PATH/WEB-INF/classes/META-INF/activiti-app

EXPOSE 8080

RUN cd $CATALINA_HOME/bin
CMD ["catalina.sh", "run"]

