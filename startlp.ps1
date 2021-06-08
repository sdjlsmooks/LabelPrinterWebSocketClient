function startlp() {
   Start-Service -DisplayName HS-AOPFrontDesk
   Start-Service -DisplayName HS-Crestone1LabelPrinter
   Start-Service -DisplayName HS-Crestone2LabelPrinter
   Start-Service -DisplayName HS-MARCLabelPrinter
   Start-Service -DisplayName HS-MedicalCenterLabelPrinter
   Start-Service -DisplayName HS-Suite200LabelPrinter
   Start-Service -DisplayName HS-YFSDUTCHCLARKLabelPrinter
   Start-Service -DisplayName HS-YFSFrontDeskLabelPrinter
   Start-Service -DisplayName HS-EDUCATIONALSERVICES
   Start-Service -DisplayName HS-CWCFrontDeskLabelPrinter
}
