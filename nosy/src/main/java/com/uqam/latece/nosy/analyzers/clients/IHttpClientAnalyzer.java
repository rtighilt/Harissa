package com.uqam.latece.nosy.analyzers.clients;

import com.uqam.latece.harissa.models.HarissaClass;

import java.util.List;

public interface IHttpClientAnalyzer
{
    String getHttpClientPackageName();

    boolean isConnectingToRESTService();

    boolean isUsingHttpCaching();

    boolean isSettingTimeout();

    boolean isMakingSynchronousCalls();

    boolean isMakingAsynchronousCalls();

    List<IClientInstance> findHttpLibraryInstances();

    List<String> findCallsDestinations();

    List<IClientInstance> getInstances();

    List<HarissaClass> getClassesReferencingPackage();





}
