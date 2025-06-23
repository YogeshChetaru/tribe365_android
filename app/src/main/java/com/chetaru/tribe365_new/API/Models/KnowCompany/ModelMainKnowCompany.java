package com.chetaru.tribe365_new.API.Models.KnowCompany;

import com.google.gson.annotations.SerializedName;

import java.util.List;


@SuppressWarnings("unused")
public class ModelMainKnowCompany {

    @SerializedName("getCOTpersonalityType")
    private GetCOTpersonalityType mGetCOTpersonalityType;
    @SerializedName("getCOTteamRoleMapReport")
    private GetCOTteamRoleMapReport mGetCOTteamRoleMapReport;
    @SerializedName("getDOTreportGraph")
    private List<GetDOTreportGraph> mGetDOTreportGraph;
    @SerializedName("getDiagnosticReportForGraph")
    private GetDiagnosticReportForGraph mGetDiagnosticReportForGraph;
    @SerializedName("getSOTcultureStructureReport")
    private GetSOTcultureStructureReport mGetSOTcultureStructureReport;
    @SerializedName("getSOTmotivationReport")
    private GetSOTmotivationReport mGetSOTmotivationReport;
    @SerializedName("getTribeometerReportForGraph")
    private GetTribeometerReportForGraph mGetTribeometerReportForGraph;

    public GetCOTpersonalityType getGetCOTpersonalityType() {
        return mGetCOTpersonalityType;
    }

    public void setGetCOTpersonalityType(GetCOTpersonalityType getCOTpersonalityType) {
        mGetCOTpersonalityType = getCOTpersonalityType;
    }

    public GetCOTteamRoleMapReport getGetCOTteamRoleMapReport() {
        return mGetCOTteamRoleMapReport;
    }

    public void setGetCOTteamRoleMapReport(GetCOTteamRoleMapReport getCOTteamRoleMapReport) {
        mGetCOTteamRoleMapReport = getCOTteamRoleMapReport;
    }

    public List<GetDOTreportGraph> getGetDOTreportGraph() {
        return mGetDOTreportGraph;
    }

    public void setGetDOTreportGraph(List<GetDOTreportGraph> getDOTreportGraph) {
        mGetDOTreportGraph = getDOTreportGraph;
    }

    public GetDiagnosticReportForGraph getGetDiagnosticReportForGraph() {
        return mGetDiagnosticReportForGraph;
    }

    public void setGetDiagnosticReportForGraph(GetDiagnosticReportForGraph getDiagnosticReportForGraph) {
        mGetDiagnosticReportForGraph = getDiagnosticReportForGraph;
    }

    public GetSOTcultureStructureReport getGetSOTcultureStructureReport() {
        return mGetSOTcultureStructureReport;
    }

    public void setGetSOTcultureStructureReport(GetSOTcultureStructureReport getSOTcultureStructureReport) {
        mGetSOTcultureStructureReport = getSOTcultureStructureReport;
    }

    public GetSOTmotivationReport getGetSOTmotivationReport() {
        return mGetSOTmotivationReport;
    }

    public void setGetSOTmotivationReport(GetSOTmotivationReport getSOTmotivationReport) {
        mGetSOTmotivationReport = getSOTmotivationReport;
    }

    public GetTribeometerReportForGraph getGetTribeometerReportForGraph() {
        return mGetTribeometerReportForGraph;
    }

    public void setGetTribeometerReportForGraph(GetTribeometerReportForGraph getTribeometerReportForGraph) {
        mGetTribeometerReportForGraph = getTribeometerReportForGraph;
    }

}
