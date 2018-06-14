package com.uqam.latece.harissa.models;

import soot.SootMethod;
import soot.jimple.JimpleBody;
import soot.tagkit.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class HarissaMethod extends HarissaEntity{

    private SootMethod sootMethod;
    private HarissaClass declaringClass;
    private HarissaBody body;
    private List<String> parameterTypes = new ArrayList<>();
    private List<Annotation> annotations = new ArrayList<>();

    public List<Annotation> getAnnotations() {
        return annotations;
    }

    public HarissaMethod(SootMethod sootMethod)
    {
        this.sootMethod = sootMethod;
        this.name = this.sootMethod.getName();
        this.buildParameters();
        this.buildAnnotations();

        if(sootMethod.hasActiveBody()) this.body = new HarissaBody((JimpleBody) this.sootMethod.retrieveActiveBody(), this);
    }

    public HarissaMethod(SootMethod sootMethod, HarissaClass declaringClass)
    {
        this.sootMethod = sootMethod;
        this.name = this.sootMethod.getName();
        this.buildParameters();
        this.declaringClass = declaringClass;
        this.buildAnnotations();

        if(sootMethod.hasActiveBody()) this.body = new HarissaBody((JimpleBody) this.sootMethod.retrieveActiveBody(), this);
    }

    public int getNumberOfLines()
    {
        if(this.hasActiveBody())
        {
            return this.sootMethod.getActiveBody().getUnits().size() - sootMethod.getParameterCount() - 1;
        }

        else return 0;

    }

    public HarissaClass getDeclaringClass()
    {
        return declaringClass;
    }
    public SootMethod getSootMethod()
    {
        return  this.sootMethod;
    }

    public HarissaBody getBody()
    {
        return this.body;
    }


    public boolean hasActiveBody()
    {
        return sootMethod.hasActiveBody();
    }
    private boolean hasTags()
    {
        return (!this.sootMethod.getTags().isEmpty());
    }
    private void buildParameters(){
        this.sootMethod.getParameterTypes().forEach(type -> this.parameterTypes.add(type.toString()));
    }

    private boolean hasAnnotations()
    {
        return this.hasTags() && this.sootMethod.getTags().stream().anyMatch(tag -> tag instanceof VisibilityAnnotationTag || tag instanceof VisibilityParameterAnnotationTag);

    }

    private void buildAnnotations()
    {
        List<AnnotationTag> annotationTags = new ArrayList<>();
        List<AnnotationTag> annotationParameterTag = new ArrayList<>();

        try{
            if(sootMethod.getName().equals("reposForUser"))
            {
                String s = "ss";
            }


        this.sootMethod.getTags()
                              .stream()
                              .filter(tag -> tag instanceof VisibilityAnnotationTag || tag instanceof VisibilityParameterAnnotationTag)
                              .forEach(tag -> {
                                  if (tag instanceof VisibilityAnnotationTag)
                                  {
                                      annotationTags.addAll(((VisibilityAnnotationTag) tag).getAnnotations());
                                  }

                                  if (tag instanceof VisibilityParameterAnnotationTag)
                                  {
                                      ((VisibilityParameterAnnotationTag) tag).getVisibilityAnnotations()
                                                                              .forEach(visibilityAnnotationTag -> {
                                                                                  annotationParameterTag.addAll(visibilityAnnotationTag.getAnnotations());
                                                                              });
                                  }
                              });

        if(!annotationTags.isEmpty())
            annotationTags.forEach(annotationTag -> addAnnotation(annotationTag, Annotation.ANNOTATION_TYPE.VISIBILITY_ANNOTATION));

        if(!annotationParameterTag.isEmpty())
            annotationParameterTag.forEach(annotationTag -> addAnnotation(annotationTag, Annotation.ANNOTATION_TYPE.VISIBILITY_PARAMETER_ANNOTATION));

        }
        catch (NullPointerException e)
        {
            String s = "ss";
            //Cath it
        }

        if(!annotationTags.isEmpty())
            annotationTags.forEach(annotationTag -> addAnnotation(annotationTag, Annotation.ANNOTATION_TYPE.VISIBILITY_ANNOTATION));

        if(!annotationParameterTag.isEmpty())
            annotationParameterTag.forEach(annotationTag -> addAnnotation(annotationTag, Annotation.ANNOTATION_TYPE.VISIBILITY_PARAMETER_ANNOTATION));

    }


    private void addAnnotation(AnnotationTag annotationTag, Annotation.ANNOTATION_TYPE annotation_type)
    {
        try
        {
        String annotationName = annotationTag.getType();
        List<String> values = annotationTag.getElems()
                                           .stream()
                                           .map(AnnotationElem::toString)
                                           .collect(Collectors.toList());

        //if(!annotationTag.getElems().isEmpty()) annotationTag.getElems().forEach(annotationElem -> values.add(annotationElem.toString()));

           this.annotations.add(new Annotation(annotationName,values,annotation_type));
       }
       catch (NullPointerException e)
       {

       }
    }

    public boolean hasAnnotationsByPackage(String type)
    {
        return this.annotations.stream()
                               .anyMatch(annotation ->
                                       annotation.getName()
                                                 .contains(type));
    }

    public String getSignature(){
        return this.sootMethod.getSignature();
    }

    public boolean hasParameterOfType(String type)
    {
      return this.parameterTypes.contains(type);
    }
}
