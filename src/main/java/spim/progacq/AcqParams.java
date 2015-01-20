package spim.progacq;

import clearvolume.volume.sink.VolumeSinkInterface;
import mmcorej.CMMCore;
import spim.setup.SPIMSetup;
import spim.setup.SPIMSetup.SPIMDevice;

import java.io.File;

public class AcqParams {
	private CMMCore			core;
	private SPIMSetup		setup;
	private AcqRow[]		rows;

	private double			timeStepSeconds;
	private int				timeSeqCount;

	private boolean			continuous;

	private AcqOutputHandler outputHandler;

	private ProgrammaticAcquisitor.AcqProgressCallback	progressListener;

	private SPIMDevice[]		metaDevices;

	private AntiDrift.Factory	adFactory;
	private boolean				updateLive;
	private boolean				illumFullStack;
	private int					zWaitMillis;

	private boolean				profile;

	private boolean				useClearVolume;

	private VolumeSinkInterface clearVolumeSink;

	public AcqParams() {
		this(null, null, null, 0D, 0, false, null, null, false, null);
	}

	public AcqParams(CMMCore icore, SPIMSetup setup, AcqRow[] rows) {
		this(icore, setup, rows, 0D, 1, false, null, rows[0].getDevices(), false, null);
	}

	public AcqParams(CMMCore core, SPIMSetup setup, AcqRow[] rows, double deltat, int count)
	{
		this(core, setup, rows, deltat, count, false, null, rows[0].getDevices(), false, null);
	}

	public AcqParams(CMMCore iCore, SPIMSetup setup, AcqRow[] iRows,
			double iTimeStep, int iTimeSeqCnt, boolean iContinuous,
			ProgrammaticAcquisitor.AcqProgressCallback iListener, SPIMDevice[] iMetaDevices, boolean saveIndv,
			File rootDir) {
		this(
			iCore,
			setup,
			iRows,
			iTimeStep,
			iTimeSeqCnt,
			iContinuous,
			iListener,
			iMetaDevices,
			(saveIndv ?
				new IndividualImagesHandler(
					rootDir,
					IndividualImagesHandler.shortNamesToScheme("PA", new boolean[] {true, false, false, true}, null)
				) :
				new OutputAsStackHandler()
			)
		);
	}

	public AcqParams(CMMCore iCore, SPIMSetup setup, AcqRow[] iRows,
			double iTimeStep, int iTimeSeqCnt, boolean iContinuous,
			ProgrammaticAcquisitor.AcqProgressCallback iListener, SPIMDevice[] iMetaDevices,
			AcqOutputHandler handler) {

		setCore(iCore);
		setRows(iRows);
		setSetup(setup);
		setTimeStepSeconds(iTimeStep);
		setTimeSeqCount(iTimeSeqCnt);
		setContinuous(iContinuous);
		setProgressListener(iListener);
		setMetaDevices(iMetaDevices);

		setOutputHandler(handler);
	}

	/**
	 * @return the core
	 */
	public CMMCore getCore() {
		return core;
	}

	/**
	 * @param core the core to set
	 */
	public void setCore(CMMCore core) {
		this.core = core;
	}

	/**
	 * @return the timeStepSeconds
	 */
	public double getTimeStepSeconds() {
		return timeStepSeconds;
	}

	/**
	 * @param timeStepSeconds the timeStepSeconds to set
	 */
	public void setTimeStepSeconds(double timeStepSeconds) {
		this.timeStepSeconds = timeStepSeconds;
	}

	/**
	 * @return the timeSeqCount
	 */
	public int getTimeSeqCount() {
		return timeSeqCount;
	}

	/**
	 * @param timeSeqCount the timeSeqCount to set
	 */
	public void setTimeSeqCount(int timeSeqCount) {
		this.timeSeqCount = timeSeqCount;
	}

	/**
	 * @return the continuous
	 */
	public boolean isContinuous() {
		return continuous;
	}

	/**
	 * @param continuous the continuous to set
	 */
	public void setContinuous(boolean continuous) {
		this.continuous = continuous;
	}

	public void setUseClearVolume(boolean useClearVolume) {
		this.useClearVolume = useClearVolume;
	}

	public boolean getUseClearVolume() {
		return this.useClearVolume;
	}

	public void setClearVolumeSink(final VolumeSinkInterface sink) {
		this.clearVolumeSink = sink;
	}

	public final VolumeSinkInterface getClearVolumeSink() {
		return this.clearVolumeSink;
	}

	/**
	 * @return the progressListener
	 */
	public ProgrammaticAcquisitor.AcqProgressCallback getProgressListener() {
		return progressListener;
	}

	/**
	 * @param progressListener the progressListener to set
	 */
	public void setProgressListener(ProgrammaticAcquisitor.AcqProgressCallback progressListener) {
		this.progressListener = progressListener;
	}

	/**
	 * @return the metaDevices
	 */
	public SPIMDevice[] getMetaDevices() {
		return metaDevices;
	}

	/**
	 * @param iMetaDevices the metaDevices to set
	 */
	public void setMetaDevices(SPIMDevice[] iMetaDevices) {
		this.metaDevices = iMetaDevices;
	}

	public AcqOutputHandler getOutputHandler() {
		return outputHandler;
	}

	public void setOutputHandler(AcqOutputHandler outputHandler) {
		this.outputHandler = outputHandler;
	}

	public boolean isAntiDriftOn() {
		return adFactory != null;
	};

	public void setAntiDrift(AntiDrift.Factory in) {
		adFactory = in;
	};

	public AntiDrift getAntiDrift(AcqRow r) {
		return (adFactory != null ? adFactory.manufacture(this, r) : null);
	};

	public AcqRow[] getRows() {
		return rows;
	};

	public void setRows(AcqRow[] irows) {
		rows = irows;
	}

	public boolean isUpdateLive() {
		return updateLive;
	}

	public void setUpdateLive(boolean updateLive) {
		this.updateLive = updateLive;
	}

	public boolean isIllumFullStack() {
		return illumFullStack;
	}

	public void setIllumFullStack(boolean illumFullStack) {
		this.illumFullStack = illumFullStack;
	}

	public int getSettleDelay() {
		return zWaitMillis;
	}

	public void setSettleDelay(int zWaitMillis) {
		this.zWaitMillis = zWaitMillis;
	}

	public SPIMSetup getSetup() {
		return setup;
	}

	public void setSetup(SPIMSetup setup) {
		this.setup = setup;
	}

	public boolean doProfiling() {
		return profile;
	}

	public void setDoProfiling(boolean profile) {
		this.profile = profile;
	}

}
